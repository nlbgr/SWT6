package swt6.spring.basics.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogService;

public class AopTest {

    @Test
    public void testAOPWithJavaConfig() {
        try (AbstractApplicationContext factory =
                     new AnnotationConfigApplicationContext(AopConfig.class)) {
            var worklogService = factory.getBean(WorkLogService.class);
            worklogService.findAllEmployees();
            try {
                worklogService.findEmployeeById(1L);
                worklogService.findEmployeeById(999L);
            } catch (EmployeeIdNotFoundException e) {
                System.out.println("TestCatchClause: EmployeeIdNotFoundException");
            }
        }
    }
}
