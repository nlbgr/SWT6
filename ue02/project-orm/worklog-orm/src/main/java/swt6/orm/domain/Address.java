package swt6.orm.domain;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Entity // nur bei eigener Tabelle nötig
@Embeddable
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // ID nur bei eigener Tabelle nötig
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    //private Long     id;
    private String   zipCode;
    private String   city;
    private String   street;

    public Address(String zipCode, String city, String street) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }
}
