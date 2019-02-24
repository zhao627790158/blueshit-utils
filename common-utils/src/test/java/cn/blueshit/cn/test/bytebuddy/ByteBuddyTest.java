package cn.blueshit.cn.test.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * Created by zhaoheng on 19/1/3.
 */
public class ByteBuddyTest {


    @Test
    public void firstTest() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello byte buddy!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        Object o = dynamicType.newInstance();
        System.out.println(o.toString());
        System.out.println(o.getClass().getClassLoader());
        Class<?> dynamicType1 = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value("Hello byte buddy!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        Object o1 = dynamicType1.newInstance();
        System.out.println(o1.toString());
        System.out.println(o1.getClass().getClassLoader());
        System.out.println(o1.getClass() == o.getClass());
        System.out.println(o1.getClass().getClassLoader() == getClass().getClassLoader());
    }


    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(MethodDelegation.to(ToStringInterceptor.class))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        Object o1 = dynamicType.newInstance();
        System.out.println(o1.toString());
    }


    @Test
    public void testUser() throws Exception {
        Class<? extends User> loaded = new ByteBuddy()
                .subclass(User.class)
                .method(ElementMatchers.isSetter().and(takesArguments(String.class)))
                .intercept(MethodDelegation.to(Intercept.class))
                .make()
                .load(Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        User user = loaded.newInstance();
        user.setAge(11);
        user.setName("gude");
        //使用当前类加载器去加载,order内部引用的USER类会使用相同的类加载器加载
        user.setOrder((Order) Class.forName("cn.blueshit.cn.test.bytebuddy.Order", true, user.getClass().getClassLoader()).newInstance());
        System.out.println(user);

        User user1 = loaded.newInstance();
        user1.setName("");
        user1.setAge(1);
        System.out.println(user1);

        User user2 = new User();
        user2.setName("");
        user2.setAge(1);
        user2.setOrder(new Order());
        System.out.println(user2);
    }


    /**
     * 重新加载类
     *
     * @throws Exception
     */
    @Test
    public void testRedefine() throws Exception {
        String toString = new ByteBuddy()
                .subclass(Object.class)
                .name("example.Type")
                .method(named("toString")).intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(toString);

        Foo dynamicFoo = new ByteBuddy()
                .subclass(Foo.class)
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!"))
                .method(named("foo")).intercept(FixedValue.value("Two!"))
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded()
                .newInstance();
        System.out.println(dynamicFoo.getClass().getClassLoader());
        System.out.println(dynamicFoo.getClass().getName());
        System.out.println(dynamicFoo.foo());
    }


    class Source {

        public Source() {
        }

        public String hello(String name) {
            return null;
        }
    }


}
