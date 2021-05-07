package com.elegion.test.behancer.ui.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.databinding.ProjectsBinding;
import com.elegion.test.behancer.ui.profile.ProfileActivity;
import com.elegion.test.behancer.ui.profile.ProfileFragment;

import javax.inject.Inject;

import toothpick.Toothpick;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsFragment extends Fragment {

//    @Inject
//    ProjectsViewModel mProjectsViewModel;
//
//    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> {
//        Intent intent = new Intent(getActivity(), ProfileActivity.class);
//        Bundle args = new Bundle();
//        args.putString(ProfileFragment.PROFILE_KEY, username);
//        intent.putExtra(ProfileActivity.USERNAME_KEY, args);
//        startActivity(intent);
//    };
//
//    static ProjectsFragment newInstance() {
//        return new ProjectsFragment();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Toothpick.inject(this, AppDelegate.getAppScope());
//        mProjectsViewModel.setOnItemClickListener(mOnItemClickListener);
//
//        ProjectsBinding binding =  ProjectsBinding.inflate(inflater, container, false);
//        binding.setVm(mProjectsViewModel);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (getActivity() != null) {
//            getActivity().setTitle(R.string.projects);
//        }
//
//        mProjectsViewModel.loadProjects();
//    }
//
//    @Override
//    public void onDetach() {
//        mProjectsViewModel.dispatchDetach();
//        super.onDetach();
//    }

    @Inject
    ProjectsViewModel mProjectsViewModel;

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle args = new Bundle();
        args.putString(ProfileFragment.PROFILE_KEY, username);
        intent.putExtra(ProfileActivity.USERNAME_KEY, args);
        startActivity(intent);
    };

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, AppDelegate.getAppScope());
        ProjectsBinding binding =  ProjectsBinding.inflate(inflater, container, false);
        binding.setVm(mProjectsViewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.projects);
        }

        mProjectsViewModel.loadProjects();
    }

    @Override
    public void onDetach() {
        mProjectsViewModel.dispatchDetach();
//        Toothpick.closeScope(ProjectsFragment.class);
        super.onDetach();
    }

}
