package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Customer;
import cn.blueshit.cn.test.bean.User;
import org.dozer.DozerBeanMapper;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaoheng on 2016/6/2.
 * 适用于 属性不匹配时的对象拷贝
 *
 */
public class DozerMapperTest {

    private DozerBeanMapper mapper;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("dozerBeanMapping.xml"));
    }

    /*
    * <bean id="dozerMapper" class="org.dozer.spring.DozerBeanMapperFactoryBean">
        <property name="mappingFiles">
            <list>
                <value>classpath*:dozer-config/dozerBeanMapping.xml</value>
            </list>
        </property>
    </bean>
    * */
    @org.junit.Test
    public void testMapping() throws Exception {
        List<Customer> list = new ArrayList<Customer>();
        User user = new User(1, "user1");
        //MM/dd/yyyy HH:mm:ss
        //user.setTimeStr("02/06/2016 13:13:11");
        user.setTimeStr("2016-05-05 15:12:11");
        Customer customer = mapper.map(user, Customer.class);
        list.add(customer);
        Customer customer1 = mapper.map(user, Customer.class);
        list.add(customer1);
        System.out.println(customer);
        System.out.println(list);

    }

}
