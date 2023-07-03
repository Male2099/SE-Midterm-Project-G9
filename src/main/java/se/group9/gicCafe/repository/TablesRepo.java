package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.Tables;

public interface TablesRepo extends JpaRepository<Tables, Integer> {
    
}
