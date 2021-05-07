package com.elegion.test.behancer.di.fragment_scope;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.ui.profile.ProfileFragment;
import com.elegion.test.behancer.ui.profile.ProfileView;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileFragmentModule {
    private BaseView mView;

    public ProfileFragmentModule(BaseView mView) {
        this.mView = mView;
    }
    @Provides
    @FragmentScope
    public ProfileView provideView() {
        return (ProfileView) mView;
    }
}
