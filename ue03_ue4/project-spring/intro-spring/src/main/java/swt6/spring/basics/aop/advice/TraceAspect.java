package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAspect {

    @Pointcut("execution(public * swt6.spring.basics.aop.logic..*.*Id*(..))")
    private void idMethods() {}

    @Pointcut("execution(public * swt6.spring.basics.aop.logic..*.*(..))")
    private void allMethods() {}

    @Pointcut("@annotation(swt6.spring.basics.aop.advice.annotations.Traceable)")
    private void traceableMethods() {} // Besser geeignet für Libs, weil User entscheiden kann was verwendet wird



    //@Before("execution(public * swt6.spring.basics.aop.logic..*.*Id*(..))")
    @Before("traceableMethods()") // vereinheitlicht Pointcut
    public void traceBefore(JoinPoint joinPoint) {
        String TargetClassName = joinPoint.getTarget().getClass().getName();
        String MethodName = joinPoint.getSignature().getName();

        System.out.printf("--> %s#%s()%n", TargetClassName, MethodName);
    }

    @After("traceableMethods()")
    public void traceAfter(JoinPoint joinPoint) {
        String TargetClassName = joinPoint.getTarget().getClass().getName();
        String MethodName = joinPoint.getSignature().getName();

        System.out.printf("<-- %s#%s()%n", TargetClassName, MethodName);
    }

    @Around("traceableMethods()")
    public Object traceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String TargetClassName = joinPoint.getTarget().getClass().getName();
        String MethodName = joinPoint.getSignature().getName();

        System.out.printf("==> %s#%s()%n", TargetClassName, MethodName);
        Object result = joinPoint.proceed(joinPoint.getArgs());
        System.out.printf("<== %s#%s()%n", TargetClassName, MethodName);

        return result;
    }

    @AfterThrowing(value = "traceableMethods()", throwing = "exception")
    public void traceException(JoinPoint joinPoint, Exception exception) {
        String TargetClassName = joinPoint.getTarget().getClass().getName();
        String MethodName = joinPoint.getSignature().getName();

        System.out.printf("<!!!> %s#%s(): %s%n", TargetClassName, MethodName, exception.getMessage());
        exception.getStackTrace();
    }
}
