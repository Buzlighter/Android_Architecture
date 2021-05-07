package com.elegion.test.behancer.ui.profile;

import com.elegion.domain.service.ProfileService;
import com.elegion.test.behancer.common.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class ProfilePresenter extends BasePresenter {

    private ProfileView pView;
    @Inject
    ProfileService mService;

    @Inject
    public ProfilePresenter() {
    }

    public void setView(ProfileView view) {
        pView = view;
    }

    public void getProfile(String userName) {
        mService.getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> pView.showRefresh())
                .doFinally(pView::hideRefresh)
                .subscribe(
                        response -> {
                            pView.showUser();
                            pView.bind(response);
                        },
                        throwable -> {
                            pView.showError();
                        });
    }
}
