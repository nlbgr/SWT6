package swt6.spring.basics.ioc.util;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Log(Log.LoggerType.CONSOLE)
public class ConsoleLogger implements Logger {

  @Setter
  private String prefix = "Log";

//  public void setPrefix(String prefix) {
//    this.prefix = prefix;
//  }

  public void log(String msg) {
    System.out.format("%s: %s%n", prefix, msg);
  }
}
