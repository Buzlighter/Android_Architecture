package com.elegion.test.behancer.common;

import androidx.fragment.app.Fragment;

/**
 * Created by Vladislav Falzan.
 */

public abstract class PresenterFragment<P extends BasePresenter> extends Fragment {

    protected abstract P getPresenter();

    @Override
    public void onDetach() {
        if (getPresenter() != null) {
            getPresenter().disposeAll();
        }
        super.onDetach();
    }
}
