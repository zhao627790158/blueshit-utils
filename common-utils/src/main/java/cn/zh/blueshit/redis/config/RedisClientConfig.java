package cn.zh.blueshit.redis.config;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class RedisClientConfig {
    private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    private int timeout = 2000;
    private int errorRetryTimes = 0;
    private String masterConfString = null;
    private String slaveConfString = null;

    public RedisClientConfig() {
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getErrorRetryTimes() {
        return errorRetryTimes;
    }

    public void setErrorRetryTimes(int errorRetryTimes) {
        this.errorRetryTimes = errorRetryTimes;
    }

    public String getMasterConfString() {
        return masterConfString;
    }

    public void setMasterConfString(String masterConfString) {
        this.masterConfString = masterConfString;
    }

    public String getSlaveConfString() {
        return slaveConfString;
    }

    public void setSlaveConfString(String slaveConfString) {
        this.slaveConfString = slaveConfString;
    }
}
