package t1.openschool.task02.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import t1.openschool.task02.dto.TaskRequestDTO;
import t1.openschool.task02.dto.TaskResponseDTO;
import t1.openschool.task02.model.Task;
import t1.openschool.task02.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void findAllShouldReturnAllTasks() {
        TaskResponseDTO task1 = TaskResponseDTO.builder()
                .id(1L)
                .title("Title1")
                .description("Test1")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();
        TaskResponseDTO task2 = TaskResponseDTO.builder()
                .id(1L)
                .title("Title2")
                .description("Test2")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();

        Mockito.when(taskRepository.findAll()).thenReturn(
                List.of(getTaskFromTaskResponseDTO(task1), getTaskFromTaskResponseDTO(task2)));
        int size = taskService.findAll().size();
        Assertions.assertEquals(2, size);
    }

    @Test
    void findByIdShouldReturnTaskById() {
        TaskResponseDTO taskRespDTO = TaskResponseDTO.builder()
                .id(1L)
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();
        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.ofNullable(getTaskFromTaskResponseDTO(taskRespDTO)));
        TaskResponseDTO task = taskService.findById(1L);
        Assertions.assertNotNull(task);
        Assertions.assertEquals(taskRespDTO, task);
    }

    @Test
    void saveShouldSaveTask() {
        TaskRequestDTO task = TaskRequestDTO.builder()
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(true)
                .build();
        taskService.save(task);
        Mockito.verify(taskRepository, Mockito.times(1)).save(getTaskFromTaskRequestDTO(task));
    }

    @Test
    void deleteShouldDeleteTaskById() {
        Task.builder()
                .id(1L)
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();
        taskService.deleteById(1L);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);

    }

    private Task getTaskFromTaskRequestDTO(TaskRequestDTO taskRequestDTO) {
        return Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .dueDate(taskRequestDTO.getDueDate())
                .completed(taskRequestDTO.getCompleted())
                .build();
    }

    private Task getTaskFromTaskResponseDTO(TaskResponseDTO taskResponseDTO) {
        return Task.builder()
                .id(taskResponseDTO.getId())
                .title(taskResponseDTO.getTitle())
                .description(taskResponseDTO.getDescription())
                .dueDate(taskResponseDTO.getDueDate())
                .completed(taskResponseDTO.getCompleted())
                .build();
    }
}
