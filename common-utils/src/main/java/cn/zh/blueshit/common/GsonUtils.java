package cn.zh.blueshit.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zhaoheng on 2016/4/25.
 */
public class GsonUtils {

    private static final String JSON_EMPTY = "{}";
    private static final String JSON_EMPTY_ARRAY = "[]";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static Gson defaultGson=getGson(DEFAULT_DATE_PATTERN);


    public static String toJson(Object target) {
        return toJson(target, (Type) null, (String) null);
    }

    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, (String) null);
    }

    public static String toJson(Object target, Type targetType, String datePattern) {

        if (null == target) {
            return JSON_EMPTY;
        }
        Gson gson = getGson(datePattern);
        String result = "{}";

        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception var7) {
            if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray()) {
                result = JSON_EMPTY_ARRAY;
            }
        }
        return result;

    }

    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (json != null && json.length() >= 1) {
            Gson gson = getGson(datePattern);
            try {
                return gson.fromJson(json, token.getType());
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }


    public static Object fromJson(String json, Type type, String datePattern) {
        if (json != null && json.length() >= 1) {
            Gson gson = getGson(datePattern);
            try {
                return gson.fromJson(json, type);
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Object fromJson(String json, Type type) {
        return fromJson(json, (Type) type, (String) null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return (T) fromJson(json, (TypeToken) token, (String) null);
    }

    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if (json != null && json.length() >= 1) {
            Gson gson = getGson(datePattern);
            try {
                return gson.fromJson(json, clazz);
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return (T) fromJson(json, (Class) clazz, (String) null);
    }

    public static Gson getGson(String datePattern) {
        if (datePattern == null || datePattern.length() < 1|| datePattern.equals(DEFAULT_DATE_PATTERN)) {
            return defaultGson;
        } else {
            GsonBuilder builder = new GsonBuilder();
            builder.setDateFormat(datePattern);
            return builder.create();
        }
    }

    public static void main(String[] args) {
        //基本的数据类型无需TypeToken
        Integer i = new Integer(1);
        String i_1 = toJson(i);
        Integer integer = fromJson(i_1, Integer.class);
        System.out.println(integer);
        //list<map>-->json
        HashMap map = new HashMap();
        map.put("id", Integer.valueOf(1));
        HashMap map2 = new HashMap();
        map2.put("id", Integer.valueOf(2));
        ArrayList list = new ArrayList();
        list.add(map);
        list.add(map2);
        String json2 = toJson(list, new TypeToken<List<HashMap>>() {
        }.getType());
        System.out.println(json2);
        //json--->list<map>
        Object o = fromJson(json2, new TypeToken<List<HashMap>>() {
        }.getType());

        HashMap zhmap = new HashMap();
        zhmap.put("test", "test111");
        String zh = toJson(zhmap, (new TypeToken<HashMap>() {
        }).getType(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(zh);
        System.out.println(toJson(zhmap));

    }
}
