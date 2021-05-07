package com.elegion.test.behancer.ui.profile;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.di.AppComponent;
import com.elegion.test.behancer.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    @Inject
    BehanceApi mApi;

    @Inject
    Storage mStorage;

//    public ProfilePresenter(Storage pStorage) {
//        this.mStorage = pStorage;
//    }

    public void getProfile(String userName) {
        mCompositeDisposable.add(mApi.getUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(userName) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(getViewState()::hideRefresh)
                .subscribe(
                        response -> {
                            getViewState().showUser();
                            getViewState().bind(response.getUser());
                        },
                        throwable -> {
                            getViewState().showError();
                        }));
    }
    public void openProjectsFragment(String username) {
        getViewState().openProjectsFragment(username);
    }

    @Override
    protected void inject(AppComponent component) {
        component.inject(this);
    }
}