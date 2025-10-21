package com.isaacggr.pmanager.domain.repository;

import com.isaacggr.pmanager.domain.entity.Project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    
    Optional<Project> findByName(String name);
}
