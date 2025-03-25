package swt6.spring.basics.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogServiceImpl;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "swt6.spring.basics.aop")
public class AopConfig {

}
