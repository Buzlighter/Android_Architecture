package com.elegion.test.behancer.common;


import com.arellomobile.mvp.MvpPresenter;
import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.di.AppComponent;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Vladislav Falzan.
 */

public abstract class BasePresenter<V extends BaseView> extends MvpPresenter<V> {

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        disposeAll();
        super.onDestroy();
    }

    public BasePresenter() {
        AppComponent component = AppDelegate.getAppComponent();
        inject(component);
    }

    protected abstract void inject(AppComponent component);
}
