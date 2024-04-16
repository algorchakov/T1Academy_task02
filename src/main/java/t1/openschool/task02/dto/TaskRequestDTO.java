package t1.openschool.task02.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean completed;
}
