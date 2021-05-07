package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter {
    private final ProfileView pView;
    private final Storage pStorage;

    public ProfilePresenter(ProfileView pView, Storage pStorage) {
        this.pView = pView;
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
