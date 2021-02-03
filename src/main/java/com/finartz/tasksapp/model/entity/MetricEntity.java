package com.finartz.tasksapp.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "metrics")
@Getter
@ToString
public class MetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Setter
    @Column(name = "sprint_label", nullable = false)
    private String sprintLabel;

    @Setter
    @Column(name = "commit", nullable = false)
    private Integer commit;

    @Setter
    @Column(name = "bug_count", nullable = false)
    private Integer bugCount;

    @Setter
    @Column(name = "complete", nullable = false)
    private Integer complete;

    @Setter
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}