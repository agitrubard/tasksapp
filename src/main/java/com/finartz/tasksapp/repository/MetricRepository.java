package com.finartz.tasksapp.repository;

import com.finartz.tasksapp.model.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<MetricEntity, Long> {

    Optional<MetricEntity> findById(Long metricId);

    Optional<List<MetricEntity>> findAllByUserId(Long userId);
}