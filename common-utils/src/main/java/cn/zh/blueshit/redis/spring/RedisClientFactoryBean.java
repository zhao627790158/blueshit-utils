package cn.zh.blueshit.redis.spring;

import cn.zh.blueshit.redis.ConnectionFactoryBuilder;
import cn.zh.blueshit.redis.RedisUtils;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

/**
 * Created by zhaoheng on 2016/5/23.
 */
public class RedisClientFactoryBean implements FactoryBean {

    private ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
    private List<String> masterConfList = null;
    private List<String> slaveConfList = null;

    public RedisClientFactoryBean() {
    }


    @Override
    public Object getObject() throws Exception {
        return new RedisUtils(this.connectionFactoryBuilder, this.masterConfList);
    }

    @Override
    public Class<?> getObjectType() {
        return RedisUtils.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMasterConfList(List<String> masterConfList) {
        this.masterConfList = masterConfList;
    }

    public void setSlaveConfList(List<String> slaveConfList) {
        this.slaveConfList = slaveConfList;
    }

    public void setMasterConfString(String string) {
        this.connectionFactoryBuilder.setMasterConfString(string);
    }

    public void setSlaveConfString(String string) {
        this.connectionFactoryBuilder.setSlaveConfString(string);
    }

    public int getErrorRetryTimes() {
        return this.connectionFactoryBuilder.getErrorRetryTimes();
    }

    public void setErrorRetryTimes(int errorRetryTimes) {
        this.connectionFactoryBuilder.setErrorRetryTimes(errorRetryTimes);
    }
}
