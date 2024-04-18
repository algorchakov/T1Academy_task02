package t1.openschool.task02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean completed;
}
