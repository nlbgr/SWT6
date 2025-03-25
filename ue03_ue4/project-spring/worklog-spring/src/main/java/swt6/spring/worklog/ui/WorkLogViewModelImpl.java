package swt6.spring.worklog.ui;

import lombok.RequiredArgsConstructor;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogService;
import swt6.util.annotation.ViewModel;

// WorkLogViewModelImpl surrounds all of its methods with a JpaInterceptor that
// that keeps the EntityManager open. This is important when lazily loaded child
// objects are accessed. Not doing so results in a LazyLoadingException.
@ViewModel
@RequiredArgsConstructor
public class WorkLogViewModelImpl implements WorkLogViewModel {
  
  private final WorkLogService workLogService;

  
  @Override
  public void saveEmployees(Employee... employees) {
    for (Employee e : employees)
      workLogService.syncEmployee(e);
  }

  @Override
  public void findAll() {
    for (Employee e : workLogService.findAllEmployees()) {
      System.out.println(e);
      e.getLogbookEntries().forEach(entry -> System.out.println("   " + entry));
    }
  }
}
