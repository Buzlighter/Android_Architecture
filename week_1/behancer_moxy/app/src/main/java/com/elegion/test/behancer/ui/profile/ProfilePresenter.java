package com.elegion.test.behancer.ui.profile;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private final Storage pStorage;

    public ProfilePresenter(Storage pStorage) {
        this.pStorage = pStorage;
    }

    public void getProfile(String userName) {
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(pStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                pStorage.getUser(userName) :
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
}