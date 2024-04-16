package t1.openschool.task02.controller;

import org.springframework.web.bind.annotation.*;
import t1.openschool.task02.dto.TaskRequestDTO;
import t1.openschool.task02.dto.TaskResponseDTO;
import t1.openschool.task02.service.TaskService;
import t1.openschool.task02.service.TaskServiceImpl;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/tasks/")
    public List<TaskResponseDTO> getAll() {
        return taskService.findAll();
    }

    @ResponseBody
    @GetMapping(path = "/tasks/{id}")
    public TaskResponseDTO getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping(path = "/tasks/")
    public void create(@RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.save(taskRequestDTO);
    }

    @PutMapping(path = "/tasks/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody TaskRequestDTO taskRequestDTO) {
        taskService.update(id, taskRequestDTO);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
