package cn.zh.blueshit.common;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhaoheng on 2016/6/13.
 *
 */
public class FastReflectUtils  extends ReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FastReflectUtils.class);
    private static String attribute;

    @SuppressWarnings("unchecked")
    public static <T> T executeSuper(String method, Object service,Object paramter,T retrunType) throws Exception {
        FastMethod serviceFastMethod = getDeclaredMethod(method,service,new Class[]{paramter.getClass().getSuperclass()});
        return (T) serviceFastMethod.invoke(service, new Object[]{paramter});
    }

    @SuppressWarnings("unchecked")
    public static <T> T execute(String method, Object service,Object paramter,T retrunType) throws Exception {
        FastMethod serviceFastMethod = getDeclaredMethod(method,service,new Class[]{paramter.getClass()});
        return (T) serviceFastMethod.invoke(service, new Object[]{paramter});
    }

    @SuppressWarnings("unchecked")
    public static <T> T executeJDK(String method, Object service, Object paramter,T retrunType) throws Exception {
        Method clzMethod = getDeclaredMethodJDK(method,service,new Class[]{paramter.getClass()});

        return (T) clzMethod.invoke(service,paramter);
    }

    public static Method getDeclaredMethodJDK(String method, Object service,Class<?> ... parameterTypes) {
        Method jdkMethod = null;
        for(Class<?> clazz = service.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()){
            try {
                return clazz.getDeclaredMethod(method, parameterTypes);
            } catch (Exception e) {
                LOG.info("invoke method {} ,cause {}  ,object {}",new Object[]{method,e.getMessage(),clazz.getName()});
                continue;
            }
        }

        return  jdkMethod;
    }

    public static FastMethod getDeclaredMethod(String method,Object object, Class<?> ... parameterTypes){
        FastMethod serviceFastMethod = null;

        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()){
            try {
                FastClass serviceFastClass = FastClass.create(clazz);
                return serviceFastClass.getMethod(method, parameterTypes);
            } catch(Error suchMethodError){
                LOG.info("invoke method {} ,cause {}  ,object {}",new Object[]{method,suchMethodError.getMessage(),clazz.getName()});
                continue;
            }
        }

        return  serviceFastMethod;
    }

    public static String buildAttribute(Object entity) throws Exception {
        Field field = getDeclaredField(attribute,entity);

        return String.valueOf(field.get(entity));
    }

    public static Field getDeclaredField(String field, Object object) {
        Field f = null;
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()){
            try {
                f = clazz.getDeclaredField(field);
                f.setAccessible(true);
                return f;
            } catch(Exception suchMethodError){
                LOG.info("invoke field {} ,cause {} ,object {}",new Object[]{field,suchMethodError.getMessage(),clazz.getName()});
                continue;
            }
        }

        return null;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }


}
