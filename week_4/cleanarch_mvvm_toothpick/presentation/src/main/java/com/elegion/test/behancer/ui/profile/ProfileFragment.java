package com.elegion.test.behancer.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.databinding.ProfileBinding;

import javax.inject.Inject;

import toothpick.Toothpick;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends Fragment {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    @Inject
    ProfileViewModel mProfileViewModel;

    static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, AppDelegate.getAppScope());

        ProfileBinding binding =  ProfileBinding.inflate(inflater, container, false);
        binding.setProfile(mProfileViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        String username = getArguments().getString(PROFILE_KEY);

        if (getActivity() != null) {
            getActivity().setTitle(username);
        }

        mProfileViewModel.setUsername(username);
        mProfileViewModel.loadProfile();
    }

    @Override
    public void onDetach() {
        mProfileViewModel.dispatchDetach();
        super.onDetach();
    }
}
