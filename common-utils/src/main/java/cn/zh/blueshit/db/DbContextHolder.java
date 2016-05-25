package cn.zh.blueshit.db;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    private static final ThreadLocal<String> tableIndexHolder = new ThreadLocal<String>();

    public static void setDbKey(String dbKey) {
        contextHolder.set(dbKey);
    }

    public static String getDbKey() {
        return (String) contextHolder.get();
    }

    public static void clearDbKey() {
        contextHolder.remove();
    }

    public static void setTableIndex(String tableIndex){
        tableIndexHolder.set(tableIndex);
    }

    public static String getTableIndex(){
        return (String) tableIndexHolder.get();
    }
    public static void clearTableIndex(){
        tableIndexHolder.remove();
    }

}
