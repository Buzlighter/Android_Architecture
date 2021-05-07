package com.elegion.test.behancer.ui.profile;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

public class ProfileViewModel {
    private Disposable mDisposable;

    @Inject
    Storage mStorage;

    @Inject
    BehanceApi mBehanceApi;

    private String mUsername;

    private ObservableField<String> mImageUrl = new ObservableField<>("");
    private ObservableField<String> mProfileName = new ObservableField<>("");
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>("");
    private ObservableField<String> mProfileLocation = new ObservableField<>("");

    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::loadProfile;


    public ProfileViewModel(String username) {
        mUsername = username;
        Toothpick.inject(this, AppDelegate.getAppScope());
    }

    public void loadProfile() {
        mDisposable = mBehanceApi.getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.set(false);
                            setupUser(response.getUser());
                        },
                        throwable -> {
                            mIsErrorVisible.set(true);
                        });
    }

    public void dispatchDetach() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }


    public String getUsername() {
        return mUsername;
    }

    public ObservableField<String> getImageUrl() {
        return mImageUrl;
    }

    public ObservableField<String> getProfileName() {
        return mProfileName;
    }

    public ObservableField<String> getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public ObservableField<String> getProfileLocation() {
        return mProfileLocation;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    private void setupUser(@NonNull User user) {
        mImageUrl.set(user.getImage().getPhotoUrl());
        mProfileName.set(user.getDisplayName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = new Date(user.getCreatedOn());
        mProfileCreatedOn.set(sdf.format(dt));
        mProfileLocation.set(user.getLocation());

    }
}
