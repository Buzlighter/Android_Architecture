package com.elegion.test.behancer.ui.profile;

import toothpick.config.Module;

public class ProfileViewModelModule extends Module {
    public ProfileViewModelModule(String username) {
        bind(ProfileViewModel.class).toInstance(new ProfileViewModel(username));
    }
}
