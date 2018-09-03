package com.java.jwt.jwtauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.jwt.jwtauth.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
