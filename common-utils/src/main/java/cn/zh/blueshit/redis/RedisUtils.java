package cn.zh.blueshit.redis;

import cn.zh.blueshit.exception.RedisAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private ShardedJedisPool writePool;
    private ShardedJedisPool readPool;
    private int errorRetryTimes;
    private ConnectionFactoryBuilder connectionFactoryBuilder;
    private List<String> masterConfList;
    private List<String> slaveConfList;


    public RedisUtils(ConnectionFactoryBuilder connectionFactoryBuilder, List<String> masterConfList) {
        this(connectionFactoryBuilder, masterConfList, (List) null);
    }

    public RedisUtils(ConnectionFactoryBuilder connectionFactoryBuilder, List<String> masterConfList, List<String> slaveConfList) {
        this.writePool = null;
        this.readPool = null;
        this.errorRetryTimes = 0;
        this.connectionFactoryBuilder = null;
        this.masterConfList = null;
        this.slaveConfList = null;
        this.connectionFactoryBuilder = connectionFactoryBuilder;
        this.masterConfList = masterConfList;
        this.slaveConfList = slaveConfList;
        this.init();
    }

    /**
     * 只支持master 没有写salve实现
     */
    private void init() {
        log.info("redisUtils init begin");
        ArrayList writeShards = null;
        ArrayList readShards = null;
        if (StringUtils.hasLength(this.connectionFactoryBuilder.getMasterConfString())) {
            this.masterConfList = Arrays.asList(this.connectionFactoryBuilder.getMasterConfString().split("(?:\\s|,)+"));
        }
        if (CollectionUtils.isEmpty(this.masterConfList)) {
            throw new ExceptionInInitializerError("masterConfString is empty！");
        } else {
            writeShards = new ArrayList();
            String rAddr;
            String[] rAddrArray;
            String host;
            int port;
            JedisShardInfo jedisShardInfo;
            Iterator itr = this.masterConfList.iterator();
            if (itr.hasNext()) {
                rAddr = (String) itr.next();
                if (rAddr != null) {
                    rAddrArray = rAddr.split(":");
                    if (rAddrArray.length == 1) {
                        throw new ExceptionInInitializerError(rAddr + " is not include host:port or host:port:passwd after split \":\"");
                    }
                    host = rAddrArray[0];
                    port = Integer.valueOf(rAddrArray[1]).intValue();
                    jedisShardInfo = new JedisShardInfo(host, port, this.connectionFactoryBuilder.getTimeout());
                    if (rAddrArray.length == 3 && StringUtils.hasLength(rAddrArray[2])) {
                        jedisShardInfo.setPassword(rAddrArray[2]);
                    }
                    writeShards.add(jedisShardInfo);
                }
            }
        }
        this.writePool = new ShardedJedisPool(this.connectionFactoryBuilder.getJedisPoolConfig(), writeShards);
        if (this.connectionFactoryBuilder.getErrorRetryTimes() > 0) {
            this.errorRetryTimes = this.connectionFactoryBuilder.getErrorRetryTimes();
            log.error("after error occured redis api retry times is " + this.errorRetryTimes);
        }

        if (this.connectionFactoryBuilder.getErrorRetryTimes() > 0 && this.readPool == null) {
            this.readPool = this.writePool;
            log.error("readPool is null and errorRetryTimes >0，readPool is set to writePool");
        }
        log.info("init end~");
    }


    /**
     * 获取数据
     * @param key
     * @return
     */
    public  String get(String key) throws  RedisAccessException{
        String value = null;
        ShardedJedis j = null;
        try {
            j = (ShardedJedis)this.writePool.getResource();
            value = j.get(key);
        } catch (Exception ex) {
            //释放redis对象
            this.writePool.returnBrokenResource(j);
            throw new RedisAccessException(ex + "," + ((JedisShardInfo)j.getShardInfo(key)).toString());
        } finally {
            //返还到连接池
            this.writePool.returnResource(j);
        }
        return value;
    }
    public Long setnx(String key, String value) throws RedisAccessException {
        boolean flag = true;
        ShardedJedis j = null;
        Long result = null;

        try {
            j = (ShardedJedis)this.writePool.getResource();
            result = j.setnx(key, value);
        } catch (Exception ex) {
            flag = false;
            //释放jedis对象
            this.writePool.returnBrokenResource(j);
            throw new RedisAccessException(ex + "," + ((JedisShardInfo)j.getShardInfo(key)).toString());
        } finally {
            if(flag) {
                ////返还到连接池
                this.writePool.returnResource(j);
            }
        }
        return result;
    }

    public List<Object> setnx(String key, int seconds, String value) throws RedisAccessException {
        boolean flag = true;
        ShardedJedis j = null;
        List result = null;
        Pipeline p = null;
        try {
            j = (ShardedJedis)this.writePool.getResource();
            p = ((Jedis)j.getShard(key)).pipelined();
            p.setnx(key, value);
            p.expire(key, seconds);
            result = p.syncAndReturnAll();
        } catch (Exception ex) {
            flag = false;
            this.writePool.returnBrokenResource(j);
            throw new RedisAccessException(ex + "," + ((JedisShardInfo)j.getShardInfo(key)).toString());
        } finally {
            if(flag) {
                this.writePool.returnResource(j);
            }
            p = null;
        }
        return result;
    }

}
