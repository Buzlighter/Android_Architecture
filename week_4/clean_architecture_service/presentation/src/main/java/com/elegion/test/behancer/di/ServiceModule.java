package com.elegion.test.behancer.di;

import com.elegion.domain.service.ProfileService;
import com.elegion.domain.service.ProfileServiceImpl;
import com.elegion.domain.service.ProjectService;
import com.elegion.domain.service.ProjectServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Module
class ServiceModule {

    @Provides
    @Singleton
    ProjectService provideProjectService(ProjectServiceImpl projectService) {
        return projectService;
    }
    @Provides
    @Singleton
    ProfileService provideProfileService(ProfileServiceImpl profileService) {
        return profileService;
    }

}
