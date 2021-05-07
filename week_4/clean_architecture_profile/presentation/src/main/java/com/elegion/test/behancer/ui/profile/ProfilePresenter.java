package com.elegion.test.behancer.ui.profile;

import com.elegion.data.Storage;
import com.elegion.data.api.BehanceApi;
import com.elegion.domain.model.user.UserResponse;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class ProfilePresenter extends BasePresenter {

    private ProfileView pView;
    @Inject
    Storage pStorage;
    @Inject
    BehanceApi mApi;

    @Inject
    ProfilePresenter() {
    }

    void setView(ProfileView view) {
        pView = view;
    }

    public void getProfile(String userName) {
        mCompositeDisposable.add(mApi.getUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(pStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                pStorage.getUser(userName) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> pView.showRefresh())
                .doFinally(pView::hideRefresh)
                .subscribe(
                        response -> {
                            pView.showUser();
                            pView.bind(response.getUser());
                        },
                        throwable -> {
                            pView.showError();
                        }));
    }
}
