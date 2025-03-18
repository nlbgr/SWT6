package swt6.spring.basics.ioc.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Named;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

@Component
@Log(Log.LoggerType.FILE)
public class FileLogger implements Logger {

  private PrintWriter writer;
  private String fileName = "log.txt";

  public FileLogger() {
    init(fileName);
  }

  public FileLogger(String fileName) {
    init(fileName);
  }

  private void init(String fileName) {
    if (writer != null) {
      writer.close();
    }

    try {
      writer = new PrintWriter(new FileOutputStream(fileName));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public void setFileName(String fileName) {
    init(fileName);
  }
  
  public void log(String msg) {
    writer.println("Log: " + msg);
    writer.flush();
  }

  @PreDestroy
  public void close() {
    writer.close();
  }
}
