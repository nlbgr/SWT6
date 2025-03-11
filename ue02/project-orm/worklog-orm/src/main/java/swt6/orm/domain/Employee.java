package swt6.orm.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.CHAR) // nur bei SINGLE_TABLE notwendig
@DiscriminatorValue(value = "E") // nur bei SINGLE_TABLE notwendig
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

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "employee"
    //        , fetch = FetchType.EAGER)
            , fetch = FetchType.LAZY)
    //@Fetch(FetchMode.JOIN)
    @Fetch(FetchMode.SELECT)
    @ToString.Exclude
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    //@OneToOne(cascade = CascadeType.ALL) // nur bei eigener Tabelle nötig
    @Embedded
    @ToString.Exclude
    private Address address;

    @ElementCollection
    @Column(name = "PHONE_NUMBER")
    private Set<String> phones = new HashSet<>();

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

    public void addPhones(final String phone) {
        this.phones.add(phone);
    }
}
