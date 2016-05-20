package cn.zh.blueshit.dbrouter;

import cn.zh.blueshit.dbrouter.annotation.DoRoute;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by zhaoheng on 2016/5/20.
 */
@Aspect
@Component
public class DBRouterInterceptor {

    private static final Logger log = LoggerFactory.getLogger(DBRouterInterceptor.class);


    //todo 不够灵活
    @Resource
    private DbRouter dbRouter;

    @Pointcut("@annotation((cn.zh.blueshit.dbrouter.annotation.DoRoute))")
    public void aopPoint() {
    }


    @Before(value = "aopPoint()")
    public Object doRoute(JoinPoint jp) throws Throwable {
        //获取目标对象的方法
        Object targer = jp.getTarget();//拦截的实体类
        String methodName = jp.getSignature().getName();//拦截的方法名称
        Object[] args = jp.getArgs();//拦截的方法参数
        //拦截方法的参数类型
        Class[] parameterTypes = ((MethodSignature) jp.getSignature()).getMethod().getParameterTypes();
        //根据类型和方法名 反射得到方法
        Method method = targer.getClass().getMethod(methodName, parameterTypes);
        DoRoute doRoute = method.getAnnotation(DoRoute.class);
        //只支持一个字段路由
        String routeField = doRoute.routeFiled();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                //查看传递的参数是否包含路由字段,反射得到对象a的b字段的值-->如: user:{id:10,name:'test'}作为参数 routeField="id"->就会得到 10
                String routeFieldValue = BeanUtils.getProperty(args[i],
                        routeField);
                if (StringUtils.isNotBlank(routeFieldValue)) {
                    if("payId".equals(routeField) || "orderId".equals(routeField))
                    {
                        //根据业务拓展
                        dbRouter.doRouteByPayId(routeFieldValue);
                        break;
                    }else
                    {
                        dbRouter.doRoute(routeFieldValue);
                        break;
                    }
                }
            }
        }
        return true;
    }


}
