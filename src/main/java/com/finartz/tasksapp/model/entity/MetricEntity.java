package com.finartz.tasksapp.model.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "metrics")
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MetricEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "sprint_label", nullable = false)
    private String sprintLabel;

    @Column(name = "commit", nullable = false)
    private Integer commit;

    @Column(name = "bug_count", nullable = false)
    private Integer bugCount;

    @Column(name = "complete", nullable = false)
    private Integer complete;

    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
}