package com.isaacggr.pmanager.infrastructure.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.isaacggr.pmanager.infrastructure.controller.RestConstants.PATH_PROJECTS;
import com.isaacggr.pmanager.domain.applicationservice.ProjectService;
import com.isaacggr.pmanager.infrastructure.dto.ProjectDTO;
import com.isaacggr.pmanager.infrastructure.dto.SaveProjectDataDTO;

import jakarta.validation.Valid;

import com.isaacggr.pmanager.domain.entity.Project;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(PATH_PROJECTS)
@RequiredArgsConstructor
public class ProjectRestResource {

  private final ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid SaveProjectDataDTO saveProjectData) {
    Project project = projectService.createProject(saveProjectData);

    return ResponseEntity
        .created(URI.create(PATH_PROJECTS + "/" + project.getId()))
        .body(ProjectDTO.create(project));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") String projectId) {
    Project project = projectService.loadProject(projectId);
    return ResponseEntity.ok(ProjectDTO.create(project));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId) {
    projectService.deleteProject(projectId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectDTO> updatetProject(
      @PathVariable("id") String projectId,
      @RequestBody @Valid SaveProjectDataDTO saveProjectData) {
    Project project = projectService.updateProject(projectId, saveProjectData);
    return ResponseEntity.ok(ProjectDTO.create(project));
  }

}
