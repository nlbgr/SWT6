package swt6.spring.worklog.api;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.dtos.EmployeeDto;
import swt6.spring.worklog.logic.WorkLogService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/worklog")
@Slf4j
@RequiredArgsConstructor
public class EmployeeRestController {

    private final WorkLogService workLogService;
    private final ModelMapper modelMapper;

    @PostConstruct
    private void postConstruct() {
        log.info("construct");
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroyed");
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    public String hello() {
        return "Hello from Spring Boot xxxxxxxxxxx";
    }

    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getEmployee() {
        return workLogService.findAllEmployees().stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).toList();
    }

    @GetMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id) {
        return workLogService.findEmployeeById(id)
                .map(employee -> ResponseEntity.ok(modelMapper.map(employee, EmployeeDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }
}
