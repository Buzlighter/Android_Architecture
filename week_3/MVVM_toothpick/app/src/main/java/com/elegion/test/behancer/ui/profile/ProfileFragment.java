package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.databinding.ProfileBinding;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.ui.projects.ProjectsViewModelModule;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends Fragment implements Refreshable {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    @Inject
    ProfileViewModel mProfileViewModel;

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }


    private String username;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            username = getArguments().getString(PROFILE_KEY);
        }

        Scope fragmentScope = Toothpick.openScope(ProfileFragment.class);
        fragmentScope.installModules(new ProfileViewModelModule(username));
        Toothpick.inject(this, fragmentScope);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileBinding binding = ProfileBinding.inflate(inflater, container, false);
        binding.setVm(mProfileViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(mProfileViewModel.getUsername());
        }

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        mProfileViewModel.loadProfile();
    }

    @Override
    public void onDetach() {
        mProfileViewModel.dispatchDetach();
        Toothpick.closeScope(ProfileFragment.class);
        super.onDetach();
    }
}
