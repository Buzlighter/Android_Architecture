package com.elegion.test.behancer.di;

import com.elegion.data.repository.ProfileDBRepository;
import com.elegion.data.repository.ProfileServerRepository;
import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.domain.repository.ProfileRepository;
import com.elegion.domain.repository.ProjectRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tanchuev on 23.04.2018.
 */

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(ProjectRepository.SERVER)
    ProjectRepository provideProjectServerRepository(ProjectServerRepository projectServerRepository) {
        return projectServerRepository;
    }

    @Provides
    @Singleton
    @Named(ProjectRepository.DB)
    ProjectRepository provideProjectDBRepository(ProjectDBRepository projectDBRepository) {
        return projectDBRepository;
    }

    @Provides
    @Singleton
    @Named(ProfileRepository.SERVER)
    ProfileRepository provideProfileServerRepository(ProfileServerRepository profileServerRepository) {
        return profileServerRepository;
    }

    @Provides
    @Singleton
    @Named(ProfileRepository.DB)
    ProfileRepository provideProfileDBRepository(ProfileDBRepository profileDBRepository) {
        return profileDBRepository;
    }
}
