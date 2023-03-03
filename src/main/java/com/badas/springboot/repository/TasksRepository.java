package com.badas.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.badas.springboot.model.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Long>{

}
