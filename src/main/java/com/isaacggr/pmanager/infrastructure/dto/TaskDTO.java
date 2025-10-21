package com.isaacggr.pmanager.infrastructure.dto;

import com.isaacggr.pmanager.domain.model.TaskStatus;

import java.util.Optional;

import com.isaacggr.pmanager.domain.entity.Task;

import lombok.Data;

@Data
public class TaskDTO {

    private final String id;
    private final String title;
    private final String description;
    private final Integer numberOfDays;
    private final TaskStatus status;
    private final MemberDTO assignedMember;
    private final ProjectDTO project;

    public static TaskDTO create(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus(),
                Optional.ofNullable(task.getAssignedMember()).map(MemberDTO::create).orElse(null),
                Optional.ofNullable(task.getProject()).map(ProjectDTO::create).orElse(null));
    }
}
