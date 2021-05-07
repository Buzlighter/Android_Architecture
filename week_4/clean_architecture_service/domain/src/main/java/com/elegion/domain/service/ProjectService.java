package com.elegion.domain.service;

import com.elegion.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

public interface ProjectService {
    Single<List<Project>> getProjects();

    void insertProjects(List<Project> projects);
}
