package com.company.taskmanagement.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "TM_PROJECT", indexes = {
        @Index(name = "IDX_TM_PROJECT_UNQ_NAME", columnList = "NAME", unique = true)
})
@Entity(name = "tm_Project")
public class Project {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "DEFAULT_PRIORITY")
    private Integer defaultPriority;

    @Column(name = "DEFAULT_PROJECT")
    private Boolean defaultProject = false;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    public TaskPriority getDefaultPriority() {
        return defaultPriority == null ? null : TaskPriority.fromId(defaultPriority);
    }

    public void setDefaultPriority(TaskPriority defaultPriority) {
        this.defaultPriority = defaultPriority == null ? null : defaultPriority.getId();
    }

    public Boolean getDefaultProject() {
        return defaultProject;
    }

    public void setDefaultProject(Boolean defaultProject) {
        this.defaultProject = defaultProject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @PostConstruct
    public void postConstruct() {
        setDefaultPriority(TaskPriority.LOW);
    }
}