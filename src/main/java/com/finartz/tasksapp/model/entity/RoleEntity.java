package com.finartz.tasksapp.model.entity;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@ToString
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private RoleType type;

    @Setter
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}