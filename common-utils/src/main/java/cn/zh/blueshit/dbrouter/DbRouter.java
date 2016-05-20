package cn.zh.blueshit.dbrouter;

/**
 * Created by zhaoheng on 2016/5/20.
 * 路由接口
 */
public interface DbRouter {


    public String doRoute(String fieldId);


    public String doRouteByPayId(String resourceCode);

}
