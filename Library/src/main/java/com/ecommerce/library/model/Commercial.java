package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commercials", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image"}))
public class Commercial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commercial_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String image;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "commercials_roles", joinColumns = @JoinColumn(name = "commercial_id", referencedColumnName = "commercial_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles;
}
