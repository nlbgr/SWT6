package swt6.spring.basics.ioc;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;

public class IocTest {

  @Test
  public void testFactoryApproach() {
    WorkLogServiceImpl workLogService = new WorkLogServiceImpl();
    workLogService.findAllEmployees();
    workLogService.findEmployeeById(1L);
  }

  @Test
  public void testXmlConfig() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/logic/applicationContext-xml-config.xml")) {
      //WorkLogService workLogServiceSetter = factory.getBean("worklogService-setter-injected", WorkLogService.class);
      //WorkLogService workLogServiceCtor = factory.getBean("worklogService-ctor-injected", WorkLogService.class);
      WorkLogService workLogServiceAnnotationConfig = factory.getBean("worklogService", WorkLogService.class);

      //workLogServiceSetter.findAllEmployees();
      //workLogServiceSetter.findEmployeeById(1L);

      //workLogServiceCtor.findAllEmployees();
      //workLogServiceCtor.findEmployeeById(1L);

      workLogServiceAnnotationConfig.findAllEmployees();
      workLogServiceAnnotationConfig.findEmployeeById(1L);
    }
  }

  @Test
  public void testJavaConfig() {
    try (AbstractApplicationContext factory = new AnnotationConfigApplicationContext(IocConfig.class)) {
      var workLogService = factory.getBean("worklogService", WorkLogService.class);

      workLogService.findAllEmployees();
      workLogService.findEmployeeById(1L);
    }
  }
}
