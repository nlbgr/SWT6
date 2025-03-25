package swt6.spring.worklog.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
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
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
@EnableAspectJAutoProxy
public class JpaConfig1 {

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // ============================== DATA ACCESS LAYER =============================

    @Bean
    public EmployeeDao employeeDaoJpa() {
        return new EmployeeDaoJpa();
    }

    //============================= BUSINESS LOGIC LAYER ============================

    @Bean
    public WorkLogService workLogService(EmployeeDao employeeDaoJpa) {
        return new WorkLogServiceImpl1(employeeDaoJpa);
    }

    // ============================== PRESENTATION LAYER  ===========================

    @Bean
    public JpaInterceptor jpaInterceptor(EntityManagerFactory entityManagerFactory) {
        return new JpaInterceptor(entityManagerFactory);
    }

    @Bean
    public WorkLogViewModel workLogViewModel(WorkLogService workLogService) {
        return new WorkLogViewModelImpl(workLogService);
    }
}