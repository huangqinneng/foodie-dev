package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 13:31
 * @Description:
 */
@Component
@Aspect
public class ServiceLogAspect {

	public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

	/**
	 * AOP通知：
	 * 1. 前置通知：在方法调用之前通知
	 * 2. 后置通知：在方法正常调用结束通知
	 * 3. 环绕通知：在方法调用前和调用后，都可以分别设置通知
	 * 4. 异常通知：在方法执行过程中发生异常通知
	 * 5. 最终通知：在方法调用之后通知
	 */


	/**
	 * 切面表达式：
	 * execution 代表所要执行的表达式主体
	 * 第一处* 表示方法返回类型，* 代表所有类型
	 * 第二处 包名，代表aop监控所在的包
	 * 第三处 .. 该包及其子包下所有的类方法
	 * 地四处 * 代表类名，* 代表所有类名
	 * 第五处 .*(..)  * 代表类中的方法名，(..) 代表方法中任何参数
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.imooc.service.impl..*.*(..))")
	public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("=======开始执行{}.{}===========",
				joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
		// 记录开始时间
		long begin = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		// 记录结束实际
		long end = System.currentTimeMillis();
		long takeTime = end - begin;
		if (takeTime > 3000) {
			log.error("======执行结束,耗时：{}毫秒======", takeTime);
		} else if (takeTime > 3000) {
			log.warn("======执行结束,耗时：{}毫秒======", takeTime);
		} else {
			log.info("======执行结束,耗时：{}毫秒======", takeTime);
		}
		return result;
	}
}
