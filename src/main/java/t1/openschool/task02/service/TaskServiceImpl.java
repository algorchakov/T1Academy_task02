package t1.openschool.task02.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import t1.openschool.task02.dto.TaskRequestDTO;
import t1.openschool.task02.dto.TaskResponseDTO;
import t1.openschool.task02.model.Task;
import t1.openschool.task02.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void update(Long id, TaskRequestDTO taskRequestDTO) {
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.setTitle(taskRequestDTO.getTitle());
                    task.setDescription(taskRequestDTO.getDescription());
                    task.setDueDate(taskRequestDTO.getDueDate());
                    task.setCompleted(taskRequestDTO.getCompleted());
                    taskRepository.save(task);
                });
    }

    @Override
    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(this::getTasksResponseDTO)
                .collect(Collectors.toList());
    }

    private TaskResponseDTO getTasksResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.getCompleted())
                .build();
    }

    @Override
    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задачи с таким id нет"));
        return getTasksResponseDTO(task);
    }

    @Override
    public void save(TaskRequestDTO taskRequestDTO) {
        taskRepository.save(getTaskFromTaskRequestDTO(taskRequestDTO));
    }

    private Task getTaskFromTaskRequestDTO(TaskRequestDTO taskRequestDTO) {
        return Task.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .dueDate(taskRequestDTO.getDueDate())
                .completed(taskRequestDTO.getCompleted())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
