package cn.blueshit.cn.test.springtest;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zhaoheng on 16/9/30.
 */
public class BeanFactoryTest {


    @Test
    public void test2() {
        System.out.println(5+12*(3+5)/7.0);
    }

    @Test
    public void test1() {
        ClassPathResource resource = new ClassPathResource("spring-config-test.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);
        factory.getBean("configurerTest");

    }


}
