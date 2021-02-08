package com.finartz.tasksapp.model.entity;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private RoleType type;

    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}