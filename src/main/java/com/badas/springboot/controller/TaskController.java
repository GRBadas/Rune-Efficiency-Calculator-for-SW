package com.badas.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.badas.springboot.exception.ResourceNotFoundException;
import com.badas.springboot.model.Tasks;
import com.badas.springboot.repository.TasksRepository;

@RestController
public class TaskController {
	
	@Autowired
	private TasksRepository tasksRepository;
	
	@GetMapping("/todo")
	public List<Tasks> getAllTasks(){
	return tasksRepository.findAll();
}
	
	@PostMapping("/todo")
	public Tasks createTask(@RequestBody Tasks task) {
		return tasksRepository.save(task);
	}
	
	@GetMapping("/todo/{id}")
	public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
		Tasks task = tasksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
		return ResponseEntity.ok(task);
	}
	
	@PutMapping("/todo/{id}")
	public ResponseEntity<Tasks> updateTask(@PathVariable Long id, @RequestBody Tasks taskDetails){
		Tasks task = tasksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
		
		task.setComment(taskDetails.getComment());
		task.setCompleted(taskDetails.getCompleted());
		task.setTask(taskDetails.getTask());
		
		Tasks updatedTask = tasksRepository.save(task);
		return ResponseEntity.ok(updatedTask);
	}
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable Long id){
		Tasks task = tasksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada" + id));
		tasksRepository.delete(task);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
