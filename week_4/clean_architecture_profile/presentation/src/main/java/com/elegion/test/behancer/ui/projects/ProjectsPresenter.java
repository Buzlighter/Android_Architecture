package com.elegion.test.behancer.ui.projects;

import com.elegion.data.Storage;
import com.elegion.data.api.BehanceApi;
import com.elegion.domain.model.project.ProjectResponse;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsPresenter extends BasePresenter {

    private ProjectsView mView;
    @Inject
    Storage mStorage;
    @Inject
    BehanceApi mApi;

    @Inject
    ProjectsPresenter() {
    }

    void setView(ProjectsView view) {
        mView = view;
    }

    void getProjects() {
        mCompositeDisposable.add(
                mApi.getProjects(com.elegion.data.BuildConfig.API_QUERY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(projectResponse -> {
                            Future<Void> future = Executors.newSingleThreadExecutor().submit(() -> {
                                mStorage.insertProjects(projectResponse);
                                return null;
                            });
                            future.get();
                        })
                        .onErrorReturn(throwable -> {
                            if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                                Future<ProjectResponse> future = Executors.newSingleThreadExecutor().submit(mStorage::getProjects);
                                return future.get();
                            }
                            return null;
                        })
                        .doOnSubscribe(disposable -> mView.showRefresh())
                        .doFinally(mView::hideRefresh)
                        .subscribe(
                                response -> mView.showProjects(response.getProjects()),
                                throwable -> mView.showError())
        );
    }

    void openProfileFragment(String username) {
        mView.openProfileFragment(username);
    }
}
