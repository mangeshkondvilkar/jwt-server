package com.java.jwt.jwtauth.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.java.jwt.jwtauth.dao.TaskRepository;
import com.java.jwt.jwtauth.entity.Task;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskRepository taskRepository;

	@PostMapping
	public ResponseEntity<?> addTask(@RequestBody Task task) {
		LOGGER.info("Saving all tasks from reposiory");
		taskRepository.save(task);
      
		URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/tasks/{id}")
                .buildAndExpand(task.getId()).toUri();

		return ResponseEntity.created(location).body(task);
	}

	@GetMapping
	public List<Task> getTasks() {
		LOGGER.info("Fetching all tasks from reposiory");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return taskRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Task getTask(@PathVariable long id) {
		Optional<Task> taskDB = taskRepository.findById(id);
		return taskDB.isPresent()? taskDB.get(): new Task("dummy task");
	}

	@PutMapping("/{id}")
	public void editTask(@PathVariable long id, @RequestBody Task task) {
		Optional<Task> existingTask = taskRepository.findById(id);
		Assert.notNull(existingTask.get(), "Task not found");
		Task taskDB = existingTask.get();
		taskDB.setDescription(task.getDescription());
		taskRepository.save(taskDB);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable long id) {
		taskRepository.deleteById(id);
	}
	
}
