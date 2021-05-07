package com.elegion.test.behancer.ui.projects;

import androidx.annotation.NonNull;

import com.elegion.domain.model.project.Project;
import com.elegion.test.behancer.common.BaseView;

import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public interface ProjectsView extends BaseView {

    void showProjects(@NonNull List<Project> projects);

    void openProfileFragment(@NonNull String username);
}
