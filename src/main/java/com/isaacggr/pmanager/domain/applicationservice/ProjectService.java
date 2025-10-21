package com.isaacggr.pmanager.domain.applicationservice;

import com.isaacggr.pmanager.domain.entity.Member;
import com.isaacggr.pmanager.domain.entity.Project;
import com.isaacggr.pmanager.domain.exception.DuplicateProjectException;
import com.isaacggr.pmanager.domain.exception.InvalidProjectStatusException;
import com.isaacggr.pmanager.domain.exception.ProjectNotFoundException;
import com.isaacggr.pmanager.domain.model.ProjectStatus;
import com.isaacggr.pmanager.domain.repository.ProjectRepository;
import com.isaacggr.pmanager.infrastructure.dto.SaveProjectDataDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberService memberService;

    @Transactional
    public Project createProject(SaveProjectDataDTO saveProjectData) {
        if (existsProjectWithName(saveProjectData.getName(), null)) {
            throw new DuplicateProjectException(saveProjectData.getName());
        }

        Project project = Project
                .builder()
                .name(saveProjectData.getName())
                .description(saveProjectData.getDescription())
                .initialDate(saveProjectData.getInitialDate())
                .finalDate(saveProjectData.getFinalDate())
                .status(ProjectStatus.PEDDING)
                .build();

        projectRepository.save(project);

        addMemberstoProject(saveProjectData.getMemberIds(), project);

        log.info("Project created: {}" + project);

        return project;
    }

    public Project loadProject(String projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Transactional
    public void deleteProject(String projectId) {
        Project project = loadProject(projectId);
        projectRepository.delete(project);
    }

    @Transactional
    public Project updateProject(String projectId, SaveProjectDataDTO saveProjectData) {
        if (existsProjectWithName(saveProjectData.getName(), projectId)) {
            throw new DuplicateProjectException(saveProjectData.getName());
        }

        Project project = loadProject(projectId);

        project.setName(saveProjectData.getName());
        project.setDescription(saveProjectData.getDescription());
        project.setInitialDate(saveProjectData.getInitialDate());
        project.setFinalDate(saveProjectData.getFinalDate());
        project.setStatus(convertToProjectStatus(saveProjectData.getStatus()));

        addMemberstoProject(saveProjectData.getMemberIds(), project);

        return project;
    }

    private ProjectStatus convertToProjectStatus(String statusStr) {
        try {
            return ProjectStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidProjectStatusException(statusStr);
        }
    }

    private boolean existsProjectWithName(String name, String idToExclude) {
        return projectRepository
                .findByName(name)
                .filter(p -> !Objects.equals(p.getId(), idToExclude))
                .isPresent();
    }

    private void addMemberstoProject(Set<String> memberIds, Project project) {

        List<Member> members = Optional
                .ofNullable(memberIds).orElse(Set.of())
                .stream()
                .map(memberService::loadMemberById)
                .collect(toList());

        project.setMembers(members);
    }
}
