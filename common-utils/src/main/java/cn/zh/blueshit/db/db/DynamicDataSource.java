package cn.zh.blueshit.db.db;

import cn.zh.blueshit.db.DbContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    /*<bean id="mysqlDataSource" class="cn.zh.blueshit.db.db.DynamicDataSource">
    <property name="targetDataSources">
    <!-- 标识符类型 -->
    <map>
    <entry key="db0" value-ref="dataSourceMPT_0" />
    <entry key="db1" value-ref="dataSourceMPT_1" />
    <entry key="db2" value-ref="dataSourceMPT_2" />
    </map>
    </property>
    <property name="defaultTargetDataSource" ref="dataSourceMPT_0" />
    </bean>*/
    @Override
    protected Object determineCurrentLookupKey() {
         return DbContextHolder.getDbKey();
    }
}
