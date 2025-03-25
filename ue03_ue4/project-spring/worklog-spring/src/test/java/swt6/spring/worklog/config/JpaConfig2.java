package swt6.spring.worklog.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.jpa.EmployeeDaoJpa;
import swt6.spring.worklog.logic.WorkLogService;
import swt6.spring.worklog.logic.impl.WorkLogServiceImpl1;
import swt6.spring.worklog.ui.WorkLogViewModel;
import swt6.spring.worklog.ui.WorkLogViewModelImpl;
import swt6.util.advice.JpaInterceptor;

@Configuration
@Import(JpaDataSourceConfig.class)
@ComponentScan(basePackageClasses = {swt6.spring.worklog.dao.EmployeeRepository.class, swt6.spring.worklog.logic.WorkLogService.class})
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
@EnableAspectJAutoProxy
public class JpaConfig2 {

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}