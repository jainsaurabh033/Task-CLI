package com.taskcli.task_manager.service;

import com.taskcli.task_manager.dto.TaskCreateRequest;
import com.taskcli.task_manager.model.*;
import com.taskcli.task_manager.repository.TaskRepository;
import com.taskcli.task_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskSuccessfully() {
        // Arrange
        Long creatorId = 1L;

        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Test Task");
        request.setDescription("This is a test");

        User mockUser = new User();
        mockUser.setId(creatorId);
        mockUser.setName("Saurabh");

        Task mockTask = new Task();
        mockTask.setId(100L);
        mockTask.setTitle("Test Task");
        mockTask.setDescription("This is a test");
        mockTask.setCreator(mockUser);

        when(userRepository.findById(creatorId)).thenReturn(Optional.of(mockUser));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        // Act
        Task result = taskService.createTask(request, creatorId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Saurabh", result.getCreator().getName());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
