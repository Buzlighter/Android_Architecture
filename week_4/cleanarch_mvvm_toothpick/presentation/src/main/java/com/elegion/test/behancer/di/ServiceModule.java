package com.elegion.test.behancer.di;

import com.elegion.domain.service.ProfileService;
import com.elegion.domain.service.ProfileServiceImpl;
import com.elegion.domain.service.ProjectService;
import com.elegion.domain.service.ProjectServiceImpl;

import toothpick.config.Module;

/**
 * Created by tanchuev on 23.04.2018.
 */

public class ServiceModule extends Module {

    public ServiceModule() {

//        bind(ProjectService.class).toInstance(new ProjectServiceImpl());
//        bind(ProfileService.class).toInstance(new ProfileServiceImpl());
        bind(ProjectService.class).to(ProjectServiceImpl.class).singletonInScope();
        bind(ProfileService.class).to(ProfileServiceImpl.class).singletonInScope();
    }
}
