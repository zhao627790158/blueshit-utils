package cn.blueshit.cn.test.filter.filternew;

import cn.blueshit.cn.test.filter.PushContext;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhaoheng on 18/2/24.
 */
public class PushTestNew {

    @Test
    public void test() throws Throwable {

        List<PushFilterNew> list = Lists.newArrayList();
        list.add(new CatFilter());
        list.add(new CatFilter());
        list.add(new CatFilter());
        list.add(new SendPushFilter());

        ServiceFilterHandler handler = FilterHandlerFactory.createHandler(list);
        boolean handle = handler.handle(new PushContext());
        System.out.println(handle);
    }


}
