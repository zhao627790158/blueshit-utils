package cn.zh.blueshit.redis;

import cn.zh.blueshit.redis.config.RedisClientConfig;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class ConnectionFactoryBuilder {
    private RedisClientConfig redisClientConfig = new RedisClientConfig();

    public ConnectionFactoryBuilder() {
    }

    public void resetRedisClientConfig() {
        this.redisClientConfig = new RedisClientConfig();
    }
    public int getErrorRetryTimes() {
        return this.redisClientConfig.getErrorRetryTimes();
    }

    public void setErrorRetryTimes(int errorRetryTimes) {
        this.redisClientConfig.setErrorRetryTimes(errorRetryTimes);
    }

    public RedisClientConfig getRedisClientConfig() {
        return this.redisClientConfig;
    }

    public void setRedisClientConfig(RedisClientConfig redisClientConfig) {
        this.redisClientConfig = redisClientConfig;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return this.redisClientConfig.getJedisPoolConfig();
    }

    public void setTimeout(int timeout) {
        this.redisClientConfig.setTimeout(timeout);
    }

    public int getTimeout() {
        return this.redisClientConfig.getTimeout();
    }


    public String getMasterConfString() {
        return this.redisClientConfig.getMasterConfString();
    }

    public void setMasterConfString(String masterConfString) {
        this.redisClientConfig.setMasterConfString(masterConfString);
    }

    public String getSlaveConfString() {
        return this.redisClientConfig.getSlaveConfString();
    }

    public void setSlaveConfString(String slaveConfString) {
        this.redisClientConfig.setSlaveConfString(slaveConfString);
    }
}
