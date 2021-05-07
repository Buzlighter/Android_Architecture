package com.elegion.test.behancer.ui.basics;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.ui.projects.ProjectsAdapter;

import io.reactivex.disposables.Disposable;

public abstract class BasicViewModel extends ViewModel {
    private Disposable mDisposable;
    private Storage mStorage;

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private LiveData<PagedList<RichProject>> mProjects;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::updateProjects;

    public BasicViewModel(Storage storage, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        this.mStorage = storage;
        this.mOnItemClickListener = onItemClickListener;
        this.mProjects = mStorage.getProjectsPaged();
    }

    public BasicViewModel(Storage storage) {
        this.mStorage = storage;
        this.mProjects = mStorage.getProjectsPaged();
    }

    public abstract void updateProjects();

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public LiveData<PagedList<RichProject>> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
