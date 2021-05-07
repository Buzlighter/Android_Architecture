package com.elegion.test.behancer.ui.profile;

import android.databinding.ObservableBoolean;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.utils.DateUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel {
    public static final String PROFILE_KEY = "PROFILE_KEY";

    private String mImageUrl;
    private String mUsername;
    private String mDisplayname;
    private String mProfileCreatedOn;
    private String mProfileLocation;

    private Storage mStorage;
    private Disposable mDisposable;



    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableBoolean mIsProfileVisible = new ObservableBoolean(false);


    public ProfileViewModel(Storage mStorage) {

        this.mStorage = mStorage;
    }


    public void getProfile(String mUsername) {
    mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
            .subscribeOn(Schedulers.io())
            .doOnSuccess(response -> mStorage.insertUser(response))
            .onErrorReturn(throwable ->
                    ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                            mStorage.getUser(mUsername) :
                            null)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(disposable -> mIsProfileVisible.set(true))
            .doFinally(() -> mIsProfileVisible.set(false))
            .subscribe(
                    response -> {
                        mIsErrorVisible.set(false);
                        bindUser(response.getUser());
                    },
                    throwable -> {
                        mIsErrorVisible.set(true);
                    });
    }


    private void bindUser(User user) {
        mImageUrl = user.getImage().getPhotoUrl();
        mUsername = user.getUsername();
        mDisplayname = user.getDisplayName();
        mProfileCreatedOn = DateUtils.format(user.getCreatedOn());
        mProfileLocation = user.getLocation();
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getDisplayname() {
        return mDisplayname;
    }

    public String getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public String getProfileLocation() {
        return mProfileLocation;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableBoolean getIsProfileVisible() {
        return mIsProfileVisible;
    }

    public void dispatchDetach() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
