package cn.blueshit.cn.test.mock;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    @Test
    public void shouldDoSomething() {
        when(list.add(any())).thenReturn(false);
        System.out.println(list.add(100));
    }


    @Test
    public void testone() {

    }


    @Ignore
    @Test
    public void testtwo() {
        throw new IllegalArgumentException("1");
    }
}
