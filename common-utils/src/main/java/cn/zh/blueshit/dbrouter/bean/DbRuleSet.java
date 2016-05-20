package cn.zh.blueshit.dbrouter.bean;

import java.util.List;

/**
 * Created by zhaoheng on 2016/5/20.
 * 匹配规则
 */
public class DbRuleSet {

    /**
     * 根据整型区间
     */
    public final static int RULE_TYPE_INT = 0;
    /**
     * 根据时间区间
     */
    public final static int RULE_TYPE_DATE = 1;
    /**
     * 根据字符串
     */
    public final static int RULE_TYPE_STR = 3;

    //分库
    public final static int ROUTE_TYPE_DB = 0;

    //分表
    public final static int ROUTE_TYPE_TABLE = 1;

    //分库分表
    public final static int ROUTE_TYPE_DBANDTABLE = 2;

    /**
     * 数据库表的逻辑KEY,与数据源MAP配置中的key一致
     */
    private List<String> dbKeyArray;

    /**
     * 数据库数量
     */
    private int dbNumber;
    /**
     * 数据表数量
     */
    private int tableNumber;
    /**
     * 数据表index样式
     */
    private String tableIndexStyle;
    /**
     * Id开始
     */
    private String routeFieldStart;
    /**
     * Id结束
     */
    private String routeFieldEnd;
    /**
     * 规则类型
     */
    private int ruleType;
    /**
     * 路由类型类型
     */
    private int routeType;

    public String getRouteFieldStart() {
        return routeFieldStart;
    }

    public void setRouteFieldStart(String routeFieldStart) {
        this.routeFieldStart = routeFieldStart;
    }

    public String getRouteFieldEnd() {
        return routeFieldEnd;
    }

    public void setRouteFieldEnd(String routeFieldEnd) {
        this.routeFieldEnd = routeFieldEnd;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public List<String> getDbKeyArray() {
        return dbKeyArray;
    }

    public void setDbKeyArray(List<String> dbKeyArray) {
        this.dbKeyArray = dbKeyArray;
    }

    public int getDbNumber() {
        return dbNumber;
    }

    public void setDbNumber(int dbNumber) {
        this.dbNumber = dbNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public String getTableIndexStyle() {
        return tableIndexStyle;
    }

    public void setTableIndexStyle(String tableIndexStyle) {
        this.tableIndexStyle = tableIndexStyle;
    }


}
