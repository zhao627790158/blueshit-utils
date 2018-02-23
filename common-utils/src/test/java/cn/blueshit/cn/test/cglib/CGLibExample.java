package cn.blueshit.cn.test.cglib;

import net.sf.cglib.asm.Type;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CGLibExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // 定义一个参数是字符串类型的setCreatedAt方法  
        InterfaceMaker im = new InterfaceMaker();
        im.add(new Signature("setCreatedAt", Type.VOID_TYPE, new Type[]{Type.getType(String.class)}), null);

        Class myInterface = im.create();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ExampleBean.class);
        enhancer.setInterfaces(new Class[]{myInterface});
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args,
                                    MethodProxy proxy) throws Throwable {

                ExampleBean bean = (ExampleBean) obj;

                // 调用字符串类型的setCreatedAt方法时，转换成Date型后调用Setter  
                if (method.getName().startsWith("setCreatedAt")
                        && args[0] != null && args[0] instanceof String) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = null;
                    try {
                        date = sdf.parse((String) args[0]);
                    } catch (final Exception e) { /* nop */ }
                    bean.setCreatedAt(date);
                    return null;

                }
                return proxy.invokeSuper(obj, args);
            }
        });

        // 生成一个Bean  
        ExampleBean bean = (ExampleBean) enhancer.create();
        bean.setId(999);

        try {
            Method method = bean.getClass().getMethod("setCreatedAt", new Class[]{String.class});
            method.invoke(bean, new Object[]{"20100531"});
        } catch (final Exception e) {
            e.printStackTrace();
        }

        System.out.printf("id : [%d] createdAt : [%s]\n", bean.getId(), bean.getCreatedAt());
    }
}

class ExampleBean implements Serializable {
    private static final long serialVersionUID = -8121418052209958014L;

    private int id;
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}  