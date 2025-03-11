package swt6.orm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "employee")
    @ToString.Exclude
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    //@OneToOne(cascade = CascadeType.ALL) // nur bei eigener Tabelle nötig
    @Embedded
    @ToString.Exclude
    private Address address;

    public Employee(final String firstName, final String lastName, final LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public void addLogbookEntry(final LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogbookEntries().remove(entry);
        }

        logbookEntries.add(entry);
        entry.setEmployee(this);
    }
}
