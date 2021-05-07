package com.elegion.test.behancer.di;

import com.elegion.test.behancer.di.fragment_scope.ProfileFragmentModule;
import com.elegion.test.behancer.di.fragment_scope.ProfileSubComponent;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.profile.ProfilePresenter;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ProfileSubComponent plus(ProfileFragmentModule profileFragmentModule);

    void inject(ProjectsFragment injector);
}
