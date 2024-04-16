package t1.openschool.task02.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import t1.openschool.task02.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();
}