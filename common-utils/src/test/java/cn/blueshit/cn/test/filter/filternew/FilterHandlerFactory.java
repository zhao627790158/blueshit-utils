package cn.blueshit.cn.test.filter.filternew;

import cn.blueshit.cn.test.filter.PushContext;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by zhaoheng on 18/2/24.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterHandlerFactory {


    public static <T extends PushFilterNew> ServiceFilterHandler createHandler(List<T> filters) {
        ServiceFilterHandler last = null;
        List<T> listCopy = Lists.newArrayList();
        listCopy.addAll(filters);
        for (int i = listCopy.size() - 1; i >= 0; i--) {
            final T filter = listCopy.get(i);
            final ServiceFilterHandler next = last;
            last = new ServiceFilterHandler() {
                @Override
                public boolean handle(PushContext context) throws Throwable {
                    return filter.invokeCanInterrupt(next, context);
                }
            };

        }
        return last;
    }


}
