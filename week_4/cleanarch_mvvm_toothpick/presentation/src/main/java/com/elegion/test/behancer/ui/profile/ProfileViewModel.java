package com.elegion.test.behancer.ui.profile;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.domain.model.user.User;
import com.elegion.domain.service.ProfileService;
import com.elegion.test.behancer.utils.DateUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel {

    private Disposable mDisposable;

    @Inject
    ProfileService mService;

    private String mUsername;

    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableField<String> mPhotoUrl = new ObservableField<>();
    private ObservableField<String> mProfileName = new ObservableField<>();
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>();
    private ObservableField<String> mProfileLocation = new ObservableField<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::loadProfile;

    @Inject
    ProfileViewModel() {
    }

    void loadProfile() {
        mDisposable = mService.getUser(mUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsErrorVisible.set(false);
                            bind(response);
                        },
                        throwable -> mIsErrorVisible.set(true));
    }

    private void bind(User user) {
        mPhotoUrl.set(user.getImage().getPhotoUrl());
        mProfileName.set(user.getDisplayName());
        mProfileCreatedOn.set(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.set(user.getLocation());
    }

    void dispatchDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    void setUsername(String username) {
        this.mUsername = username;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableField<String> getPhotoUrl() {
        return mPhotoUrl;
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

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
