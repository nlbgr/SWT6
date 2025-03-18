package swt6.spring.basics.hello;

import lombok.Setter;

public class GreetingServiceImpl implements GreetingService {
    @Setter
    private String message = "<default>";

    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
