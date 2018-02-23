package cn.blueshit.cn.test.filter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhaoheng on 18/2/1.
 */
public class ServiceFilterHander {

    private List<PushFilter> filterList = new LinkedList<PushFilter>();

    private int index = 0;

    public ServiceFilterHander addFilter(PushFilter filter) {
        filterList.add(filter);
        return this;
    }

    public ServiceFilterHander addFilterCollection(Collection filters) {
        filterList.addAll(filters);
        return this;
    }

    public boolean hander(PushContext context) {
        //提前中止
        if (context.isStop()) {
            return false;
        }

        if (index < filterList.size()) {
            return filterList.get(index++).invokeCanInterrupt(this, context);
        }

        return true;
    }

}
