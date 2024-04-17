package t1.openschool.task02.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.task02.dto.TaskRequestDTO;
import t1.openschool.task02.dto.TaskResponseDTO;
import t1.openschool.task02.service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @MockBean
    TaskServiceImpl taskService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTasks() throws Exception {
        var taskList = List.of(new TaskResponseDTO(1L, "Title#1", "Test",
                        LocalDate.of(2024, 10, 11), false),
                new TaskResponseDTO(2L, "Title#2", "Test",
                        LocalDate.of(2025, 10, 11), false));

        when(taskService.findAll()).thenReturn(taskList);
        mockMvc.perform(get("/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        verify(taskService, times(1)).findAll();
    }

    @Test
    void getTaskById() throws Exception {
        TaskResponseDTO task = TaskResponseDTO.builder()
                .id(1L)
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();
        when(taskService.findById(1L)).thenReturn(task);
        mockMvc.perform(get("/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Test"))
                .andExpect(jsonPath("$.dueDate").value("2024-10-10"))
                .andExpect(jsonPath("$.completed").value(false));
        verify(taskService, times(1)).findById(1L);
    }

    @Test
    void createTask() throws Exception {
        TaskRequestDTO task = TaskRequestDTO.builder()
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();
        String taskJSON = objectMapper.writeValueAsString(task);
        mockMvc.perform(post("/tasks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJSON))
                .andExpect(status().isCreated());
        verify(taskService, times(1)).save(task);
    }

    @Test
    void updateTask() throws Exception {
        TaskResponseDTO taskResp = TaskResponseDTO.builder()
                .id(1L)
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();

        TaskRequestDTO taskReq = TaskRequestDTO.builder()
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(true)
                .build();

        when(taskService.findById(1L)).thenReturn(taskResp);

        String taskReqJSON = objectMapper.writeValueAsString(taskReq);
        mockMvc.perform(put("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskReqJSON))
                .andExpect(status().isOk());
        verify(taskService, times(1)).update(1L, taskReq);
    }

    @Test
    void deleteTask() throws Exception {
        TaskResponseDTO taskResp = TaskResponseDTO.builder()
                .id(1L)
                .title("Title")
                .description("Test")
                .dueDate(LocalDate.of(2024, 10, 10))
                .completed(false)
                .build();

        when(taskService.findById(1L)).thenReturn(taskResp);
        mockMvc.perform(delete("/tasks/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk());
        verify(taskService, times(1)).deleteById(1L);
    }
}
