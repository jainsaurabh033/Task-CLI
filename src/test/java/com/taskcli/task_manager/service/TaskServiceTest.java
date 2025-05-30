
package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.TaskCreateRequest;
import com.taskcli.task_manager.dto.TaskHistoryResponse;
import com.taskcli.task_manager.model.*;
import com.taskcli.task_manager.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;
    @Mock private FeedbackRepository feedbackRepository;
    @Mock private SprintRepository sprintRepository;
    @Mock private SubtaskRepository subtaskRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private TaskCreateRequest request;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Saurabh");

        request = new TaskCreateRequest();
        request.setTitle("Test Task");
        request.setDescription("Description");
        request.setDueDate(LocalDate.now());
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task task = taskService.createTask(request, 1L);
        assertEquals("Test Task", task.getTitle());
        assertEquals(user, task.getCreator());
    }

    @Test
    void shouldThrowIfUserNotFoundOnCreateTask() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.createTask(request, 1L));
    }

    @Test
    void shouldThrowIfSprintNotFoundOnCreateTask() {
        request.setSprintId(10L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sprintRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.createTask(request, 1L));
    }

    @Test
    void shouldGetPaginatedTasks() {
        Page<Task> mockPage = new PageImpl<>(List.of(new Task()));
        when(taskRepository.findByCreatorIdWithUser(eq(1L), any(PageRequest.class))).thenReturn(mockPage);

        Page<Task> result = taskService.getTasksByUser(1L, 0, 10);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldGetCompletedTaskHistory() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Complete");
        task.setDescription("Done");
        task.setDueDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findByCreatorIdAndStatus(1L, TaskStatus.COMPLETED)).thenReturn(List.of(task));
        when(feedbackRepository.findByTaskAndPublicFeedbackFalse(task)).thenReturn(Optional.empty());

        List<TaskHistoryResponse> responses = taskService.getCompletedTaskHistoryForUser(1L);
        assertEquals(1, responses.size());
        assertEquals("Complete", responses.get(0).getTitle());
    }

    @Test
    void shouldThrowIfUserNotFoundForHistory() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.getCompletedTaskHistoryForUser(1L));
    }

    @Test
    void shouldMarkTaskAndSubtasksCompleted() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.PENDING);

        Subtask sub1 = new Subtask();
        sub1.setCompleted(false);
        Subtask sub2 = new Subtask();
        sub2.setCompleted(false);
        task.setSubtasks(List.of(sub1, sub2));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.markTaskAsCompleted(1L);

        assertEquals(TaskStatus.COMPLETED, result.getStatus());
        assertTrue(result.getSubtasks().stream().allMatch(Subtask::isCompleted));
    }

    @Test
    void shouldThrowIfTaskNotFoundOnMarkComplete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.markTaskAsCompleted(1L));
    }
}
