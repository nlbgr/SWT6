package swt6.orm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LogbookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    //@ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    //@Fetch(FetchMode.SELECT)
    @ToString.Exclude
    private Employee employee;

    public LogbookEntry(final String activity, final LocalDateTime start, final LocalDateTime end) {
        this.activity = activity;
        this.startTime = start;
        this.endTime = end;
    }
}
