package cn.myxinge.common;

import cn.myxinge.utils.JedisUtil;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by chenxinghua on 2017/12/11.
 * <p>
 * Service 层添加 redis的缓存 机制
 */
@Aspect
@Configuration
public class ServiceAspectToRedis {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceAspectToRedis.class);

    /*
     * 定义一个切入点
     */
    // @Pointcut("execution (* findById*(..))")
    @Pointcut("execution(* cn.myxinge.service.impl.BlogServiceImpl.*(..))")
    public void excudeService() {
    }

    //配置环绕通知
    @Around("excudeService()")
    public Object twiceAsOld(ProceedingJoinPoint joinPoint) throws Exception {
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        String key = joinPoint.getSignature() + getArgsStr(joinPoint.getArgs());
        String cache = null;
        try {
            cache = JedisUtil.getDataFromRedis(key);
        } catch (Exception e) {
            LOG.error("redis获取缓存失败，发生异常。", e);
        }
        //缓存存在，直接返回
        if (null != cache) {
            LOG.info("当前方法：" + currentMethod.getName() + ",从Redis中获取数据成功.");
            if (currentMethod.getReturnType() == JSONObject.class) {
                return JSONObject.parseObject(cache);
            }
            return cache;
        } else {//不存在查询后返回
            try {
                Object proceed = joinPoint.proceed();
//                if (proceed != null) {
//                    JedisUtil.cachData(key, proceed.toString());
//                }
                return proceed;
            } catch (Throwable e) {
                LOG.error("\n----->method："
                        + joinPoint.getSignature()
                        + "thows Exception： " + e);
            } finally {

            }
        }
        return null;
    }

    /**
     * 返回参数字符串
     *
     * @return
     */
    private String getArgsStr(Object[] args) {
        if (null == args || args.length == 0) {
            return "no args input..";
        }

        StringBuffer sb = new StringBuffer();
        for (Object o : args) {
            if (null == o) {
                sb.append("| null");
                continue;
            }

            sb.append("|" + o.toString());
        }

        return sb.toString();
    }
}
