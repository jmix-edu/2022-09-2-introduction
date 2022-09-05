package com.company.taskmanagement.app;

import com.company.taskmanagement.entity.Project;
import com.company.taskmanagement.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("tm_TaskImportService")
public class TaskImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskImportService.class);

    @Autowired
    private DataManager dataManager;

    public int importTasks() {
        List<String> taskNames = obtainExternalTaskNames();
        Project project = loadDefaultProject();

        List<Task> tasks = taskNames.stream()
                .map(name -> {
                    Task task = dataManager.create(Task.class);
                    task.setName(name);
                    task.setProject(project);
                    return task;
                })
                .collect(Collectors.toList());

        EntitySet entitySet = dataManager.save(new SaveContext().saving(tasks.toArray()));
        log.info("{} tasks imported", entitySet.size());
        return entitySet.size();
    }

    private List<String> obtainExternalTaskNames() {
        List<String> taskNames = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            taskNames.add("Task " + RandomStringUtils.randomAlphabetic(5));
        }
        return taskNames;
    }

    @Nullable
    private Project loadDefaultProject() {
        return dataManager.load(Project.class)
                .query("select p from tm_Project p where p.defaultProject = :defaultProject")
                .parameter("defaultProject", true)
                .optional().orElse(null);
    }
}