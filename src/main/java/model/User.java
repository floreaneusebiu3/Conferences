package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String userId;
    private String firstName;
    private String lastName;
    int age;
    private String mail;
    private String username;
    private String password;
    private String role;
    private boolean approved;
}
