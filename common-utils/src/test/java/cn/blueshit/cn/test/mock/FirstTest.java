package cn.blueshit.cn.test.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by zhaoheng on 17/1/30.
 */
@RunWith(MockitoJUnitRunner.class)
public class FirstTest {

    @Mock
    private List list;

    /*@Spy
    当我们对@Mock的类（@Mock private OrderDao dao;）进行模拟方法时，会像下面这样去做：

    when(dao.getOrder()).thenReturn("returened by mock "); // 或者使用更为推荐的given方法
    但如果想对@Spy的类（@Spy private PriceService ps;）进行模拟方法时，需要像下面一样去做：

    doReturn("twotwo").when(ps).getPriceTwo();
    原因：

    使用@Mock生成的类，所有方法都不是真实的方法，而且返回值都是NULL。

    使用@Spy生成的类，所有方法都是真实方法，返回值都是和真实方法一样的。

    所以，你用when去设置模拟返回值时，它里面的方法（dao.getOrder()）会先执行一次。

    使用doReturn去设置的话，就不会产生上面的问题，因为有when来进行控制要模拟的方法，所以不会执行原来的方法。
*/


    @InjectMocks
    private MockService service;

    /*@Before
    public void bef() {
    //使用@RunWith(MockitoJUnitRunner.class) 后,无需下面操作
        MockitoAnnotations.initMocks(this);
    }*/

    public void bef() {
        //使用.PowerMockito 模拟static方法

    }

    @Test
    public void shouldDoSomething() {
        when(list.add(any())).thenReturn(false);
        System.out.println(list.add(100));
    }


    @Test
    public void testone() {
        when(list.add(any())).thenReturn(true);
        System.out.println(service.getList().add(100));
    }


    @Ignore
    @Test
    public void testtwo() {
        throw new IllegalArgumentException("1");
    }
}
