package swt6.spring.basics.ioc.util;

import jakarta.inject.Qualifier;

import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Log {
    LoggerType value() default LoggerType.CONSOLE;

    public enum LoggerType {
        CONSOLE,
        FILE
    }
}
