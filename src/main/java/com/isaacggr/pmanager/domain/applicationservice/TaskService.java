package com.isaacggr.pmanager.domain.applicationservice;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.isaacggr.pmanager.domain.entity.Member;
import com.isaacggr.pmanager.domain.entity.Project;
import com.isaacggr.pmanager.domain.entity.Task;
import com.isaacggr.pmanager.domain.exception.InvalidTaskStatusException;
import com.isaacggr.pmanager.domain.exception.TaskNotFoundException;
import com.isaacggr.pmanager.domain.model.TaskStatus;
import com.isaacggr.pmanager.domain.repository.TaskRepository;
import com.isaacggr.pmanager.infrastructure.config.AppConfigProperties;
import com.isaacggr.pmanager.infrastructure.dto.SaveTaskDataDTO;
import com.isaacggr.pmanager.infrastructure.util.PaginationHelper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final MemberService memberService;
    private final AppConfigProperties props;

    @Transactional
    public Task createTask(SaveTaskDataDTO saveTaskData) {

        Project project = null;

        if (!Objects.isNull(saveTaskData.getProjectId())) {
            project = projectService.loadProject(saveTaskData.getProjectId());
        }

        Member member = null;

        if (!Objects.isNull(saveTaskData.getMemberId())) {
            member = memberService.loadMemberById(saveTaskData.getMemberId());
        }

        Task task = Task
                .builder()
                .title(saveTaskData.getTitle())
                .description(saveTaskData.getDescription())
                .numberOfDays(saveTaskData.getNumberOfDays())
                .status(TaskStatus.PEDDING)
                .project(project)
                .assignedMember(member)
                .build();

        taskRepository.save(task);
        return task;
    }

    public Page<Task> findTasks(
            String projectId,
            String memberId,
            String statusStr,
            String partialTitle,
            Integer page,
            String directionStr,
            List<String> properties) {
        return taskRepository.find(
                projectId,
                memberId,
                Optional.ofNullable(statusStr).map(this::convertToTaskStatus).orElse(null),
                partialTitle,
                PaginationHelper.createPageable(page, props.getGeneral().getPageSize(), directionStr, properties));
    }

    public Task loadTask(String taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Transactional
    public void deleteTask(String taskId) {
        Task task = loadTask(taskId);
        taskRepository.delete(task);
    }

    @Transactional
    public Task updateTask(String taskId, SaveTaskDataDTO saveTaskData) {

        Project project = null;
        if (!Objects.isNull(saveTaskData.getProjectId())) {
            project = projectService.loadProject(saveTaskData.getProjectId());
        }

        Member member = null;
        if (!Objects.isNull(saveTaskData.getMemberId())) {
            member = memberService.loadMemberById(saveTaskData.getMemberId());
        }

        Task task = loadTask(taskId);

        task.setTitle(saveTaskData.getTitle());
        task.setDescription(saveTaskData.getDescription());
        task.setNumberOfDays(saveTaskData.getNumberOfDays());
        task.setStatus(convertToTaskStatus(saveTaskData.getStatus()));
        task.setProject(project);
        task.setAssignedMember(member);
        return task;
    }

    private TaskStatus convertToTaskStatus(String statusStr) {
        try {
            return TaskStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidTaskStatusException(statusStr);
        }
    }
}
