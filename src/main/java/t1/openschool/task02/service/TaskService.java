package t1.openschool.task02.service;

import t1.openschool.task02.dto.TaskRequestDTO;
import t1.openschool.task02.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    void update(Long id, TaskRequestDTO taskRequestDTO);

    List<TaskResponseDTO> findAll();

    TaskResponseDTO findById(Long id);

    void save(TaskRequestDTO taskRequestDTO);

    void deleteById(Long id);
}
