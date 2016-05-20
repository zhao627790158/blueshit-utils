package cn.zh.blueshit.dbrouter.db;

import cn.zh.blueshit.dbrouter.DbContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
         return DbContextHolder.getDbKey();
    }
}
