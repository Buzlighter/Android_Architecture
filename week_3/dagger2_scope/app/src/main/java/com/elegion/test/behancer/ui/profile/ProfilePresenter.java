package com.elegion.test.behancer.ui.profile;

import android.view.View;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {

    @Inject
    ProfileView pView;

    @Inject
    Storage pStorage;

    @Inject
    BehanceApi mApi;

    @Inject
    public ProfilePresenter() {
    }


//    public void setView(ProfileView view) {
//        this.pView = view;
//    }

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
