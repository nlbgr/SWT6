package org.swt6.orm.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {
    @Id @GeneratedValue
    private String name;

    @Embedded
    //@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@Fetch(FetchMode.SELECT)
    private PersonDetail personDetail;

    public void addPersonDetail(PersonDetail personDetail) {
        this.personDetail = personDetail;
    }
}
