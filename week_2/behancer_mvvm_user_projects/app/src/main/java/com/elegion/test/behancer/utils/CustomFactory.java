package com.elegion.test.behancer.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.projects.ProjectsAdapter;
import com.elegion.test.behancer.ui.projects.ProjectsViewModel;
import com.elegion.test.behancer.ui.user_projects.UserProjectsViewModel;

/**
 * @author Azret Magometov
 */
public class CustomFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;
    private String mUsername;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;


    public CustomFactory(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener){
        mStorage = storage;
        mOnItemClickListener = onItemClickListener;
    }

    public CustomFactory(Storage storage, String username){
        mStorage = storage;
        mUsername = username;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(mOnItemClickListener != null) {
            return (T) new ProjectsViewModel(mStorage, mOnItemClickListener);
        }
        else {
            return (T) new UserProjectsViewModel(mStorage, mUsername);
        }
    }
}
