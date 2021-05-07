package com.elegion.test.behancer.ui.profile;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.User;

public interface ProfileView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void bind(User user);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showUser();
    @StateStrategyType(AddToEndSingleStrategy.class)
    void openProjectsFragment(@NonNull String username);
}
