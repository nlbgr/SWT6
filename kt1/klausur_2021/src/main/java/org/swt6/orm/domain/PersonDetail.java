package org.swt6.orm.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
//@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonDetail {
    //@Id
    //@GeneratedValue
    //private double weight;

    @OneToOne
    private Person person;
}
