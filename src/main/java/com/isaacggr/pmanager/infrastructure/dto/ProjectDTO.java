package com.isaacggr.pmanager.infrastructure.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

import com.isaacggr.pmanager.domain.entity.Member;
import com.isaacggr.pmanager.domain.entity.Project;
import com.isaacggr.pmanager.domain.model.ProjectStatus;

import lombok.Data;

@Data
public class ProjectDTO {

    private final String id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalDate;
    private final ProjectStatus status;
    private final Set<String> memberIds;

    public static ProjectDTO create(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate(),
                project.getStatus(),
                Optional
                        .ofNullable(project.getMembers())
                        .orElse(List.of())
                        .stream()
                        .map(Member::getId)
                        .collect(toSet()));

    }
}
