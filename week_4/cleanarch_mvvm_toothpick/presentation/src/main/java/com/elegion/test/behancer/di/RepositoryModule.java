package com.elegion.test.behancer.di;

import com.elegion.data.repository.ProfileDBRepository;
import com.elegion.data.repository.ProfileServerRepository;
import com.elegion.data.repository.ProjectDBRepository;
import com.elegion.data.repository.ProjectServerRepository;
import com.elegion.domain.repository.ProfileRepository;
import com.elegion.domain.repository.ProjectRepository;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class RepositoryModule extends Module {

    public RepositoryModule() {
        bind(ProjectRepository.class).withName(ProjectRepository.SERVER).to(ProjectServerRepository.class).singletonInScope();
        bind(ProjectRepository.class).withName(ProjectRepository.DB).to(ProjectDBRepository.class).singletonInScope();
        bind(ProfileRepository.class).withName(ProfileRepository.SERVER).to(ProfileServerRepository.class).singletonInScope();
        bind(ProfileRepository.class).withName(ProfileRepository.DB).to(ProfileDBRepository.class).singletonInScope();
    }
}
