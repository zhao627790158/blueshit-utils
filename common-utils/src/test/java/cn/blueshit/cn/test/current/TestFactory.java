package cn.blueshit.cn.test.current;

/**
 * Created by zhaoheng on 16/8/21.
 * 延长初始化占用类模式
 */
public class TestFactory {

    private static class CacheHolder {
        public static CurrentMapCache<String, Object> cache = new CurrentMapCache<String, Object>();
    }


    public static CurrentMapCache getCache() {
        return CacheHolder.cache;
    }


}
