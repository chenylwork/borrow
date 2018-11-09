package com.work.borrow.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * 日志切入
 * @Aspect注解
 * @Poincut注解表示切入的点，即程序中通用的逻辑业务，这里是请求的路径
 * @Before注解表示当前方法是在具体的请求方法之前执行
 * @After注解表示当前方法是在具体的请求方法之后 执行
 * @AfterReturning注解可以得到请求的参数
 */
@Aspect
@Configuration
public class LoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };
    /**
     * 设置切入点
     */
    @Pointcut("execution(public * com.work.borrow.service.*.*(..))")
    public void job() {}

    @Before("job()")
    public void doBefore(JoinPoint joinPoint) {
        String classType = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
//        Class<?>[] classes = new Class[args.length];
//        for (int k = 0; k < args.length; k++) {
//            if (!args[k].getClass().isPrimitive()) {
//                //获取的是封装类型而不是基础类型
//                String result = args[k].getClass().getName();
//                Class s = map.get(result);
//                classes[k] = s == null ? args[k].getClass() : s;
//            }
//        }
        String[] paramsNames = null;
        logger.info("执行方法："+classType+"."+methodName);
        StringBuffer params = new StringBuffer();
//        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
//        try {
//            Method method = Class.forName(classType).getMethod(methodName, classes);
//            paramsNames = parameterNameDiscoverer.getParameterNames(method);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ListIterator<String> paramsIterator = Arrays.asList(paramsNames).listIterator();
        Arrays.asList(args).forEach(data -> {
            // 键
//            params.append(paramsIterator.next()+"：");
            // 值
            if (data instanceof MultipartFile) {
                params.append("文件是否空："+(data == null));
            } else {
                params.append(data);
            }
            params.append(";");
        });
        logger.info("请求参数："+ params.toString());
    }
    @After("job()")
    public void doAfter() {}

    @AfterReturning(returning="obj", pointcut = "job()")
    public void doAfterReturnig(Object obj){
        logger.info("返回结果：{}", obj);
    }


}
