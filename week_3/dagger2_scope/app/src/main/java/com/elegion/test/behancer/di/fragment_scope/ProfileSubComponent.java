package com.elegion.test.behancer.di.fragment_scope;


import com.elegion.test.behancer.ui.profile.ProfileFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {ProfileFragmentModule.class})
public interface ProfileSubComponent {
    void inject(ProfileFragment profileFragment);
}
