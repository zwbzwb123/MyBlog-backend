package com.zwb.blog.config.aspect;




import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class SentinelSourceAspect {


    @Around(value = "@annotation(com.alibaba.csp.sentinel.annotation.SentinelResource)")
    public Object RateLimiter(ProceedingJoinPoint jp) throws Throwable {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Class<?> clazz = jp.getTarget().getClass();
        Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
        SentinelResource sr = m.getAnnotation(SentinelResource.class);
        String value = sr.value();
        Object[] args = jp.getArgs();
        try (Entry e = SphU.entry(value)){
            return jp.proceed(args);
        }catch (BlockException e) {
            Method[] methods = clazz.getMethods();
            for (Method me : methods){
                if (me.getName().startsWith(sr.blockHandler())){
                    return me.invoke(jp.getTarget(),args);
                }
            }
            return null;
        }
    }
}
