package org.swt6.orm.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Project {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Set<Bug> bugs = new HashSet<Bug>();

    @ManyToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Mitarbeiter> mitarbeiters = new ArrayList<>();
}
