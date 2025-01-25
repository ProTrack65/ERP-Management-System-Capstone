package com.ust.project_service.service;

import com.ust.project_service.entity.Project;

import java.util.List;

public interface ProjectService {
    Project addProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    Project updateProject(Long id, Project project);

    void deleteProject(Long id);
}
