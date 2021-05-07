package com.elegion.test.behancer.di;

import com.elegion.test.behancer.ui.profile.ProfilePresenter;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(ProjectsFragment injector);

    void inject(ProfilePresenter profilePresenter);
    void inject(ProjectsPresenter projectsPresenter);
}
