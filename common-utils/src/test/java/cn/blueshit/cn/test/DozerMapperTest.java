package cn.blueshit.cn.test;

import cn.blueshit.cn.test.bean.Customer;
import cn.blueshit.cn.test.bean.User;
import org.dozer.DozerBeanMapper;
import org.junit.*;

import java.util.Arrays;

/**
 * Created by zhaoheng on 2016/6/2.
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
        User user = new User(1, "user1");
        //MM/dd/yyyy HH:mm:ss
        //user.setTimeStr("02/06/2016 13:13:11");
        user.setTimeStr("2016-05-05 15:12:11");
        Customer customer = mapper.map(user, Customer.class);
        System.out.println(customer);

    }

}
