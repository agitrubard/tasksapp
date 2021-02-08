package com.finartz.tasksapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "users")
@ToString(of = {"id", "name", "surname", "email"})
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private RoleEntity role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MetricEntity> metrics;


    public UserEntity(UserEntity user) {
        this.id = user.getId();
        this.name = user.getSurname();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.metrics = user.getMetrics();
    }
}