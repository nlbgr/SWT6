package org.swt6.orm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bug {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @ManyToOne
    private Mitarbeiter mitarbeiter;

    @ManyToOne
    private Project project;
}
