package swt6.spring.basics.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogServiceImpl;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses = {swt6.spring.basics.ioc.util.Logger.class, swt6.spring.basics.ioc.logic.javaconfig.WorkLogServiceImpl.class})
public class IocConfig {
    // nicht notwendig wegen ComponentScan
//    @Bean
//    public Logger consoleLogger() {
//        return new ConsoleLogger();
//    }
//
//    @Bean
//    public Logger fileLogger() {
//        return new FileLogger("log.txt");
//    }

    @Bean("worklogService")
    public WorkLogService getWorkLogService(@Log(Log.LoggerType.CONSOLE) Logger logger) {
        return new WorkLogServiceImpl(logger);
    }
}
