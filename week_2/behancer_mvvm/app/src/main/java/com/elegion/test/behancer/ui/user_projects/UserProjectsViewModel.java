package com.elegion.test.behancer.ui.user_projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.project.RichProject;
import com.elegion.test.behancer.ui.basics.BasicViewModel;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserProjectsViewModel extends BasicViewModel {

    private Storage mStorage;
    private String mUsername;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private LiveData<PagedList<RichProject>> mProjects;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::updateProjects;
    private Disposable mDisposable;


    public UserProjectsViewModel(Storage storage, String username) {
        super(storage);
        this.mStorage = storage;
        this.mUsername = username;
        mProjects = mStorage.getProjectsPaged(username);
        updateProjects();
    }

    @Override
    public void updateProjects() {
        mIsLoading.setValue(false);
        mIsErrorVisible.setValue(false);
        mDisposable = ApiUtils.getApiService().getUserProjects(mUsername)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {mStorage.insertProjects(response);
                        mStorage.getProjectsPaged(mUsername);},
                        throwable -> {
                            boolean value = mProjects.getValue() == null || mProjects.getValue().size() == 0;
                            mIsErrorVisible.postValue(value);
                        });

    }
}
