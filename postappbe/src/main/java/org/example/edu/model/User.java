package org.example.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "USER_FIRSTNAME")
    private String firstName;

    @Column(name = "USER_LASTNAME")
    private String lastName;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;
}
