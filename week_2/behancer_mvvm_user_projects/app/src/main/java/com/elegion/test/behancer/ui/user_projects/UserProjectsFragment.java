package com.elegion.test.behancer.ui.user_projects;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.ui.basics.BasicFragment;
import com.elegion.test.behancer.utils.CustomFactory;

public class UserProjectsFragment extends BasicFragment {

    public static final String PROFILE_KEY = "PROFILE_KEY";
    String mUsername;

    public static UserProjectsFragment newInstance(Bundle args) {
        UserProjectsFragment fragment = new UserProjectsFragment();
        fragment.setArguments(args);

        return fragment;
    }

//    private UserProjectsViewModel mUserProjectsViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mUsername = getArguments().getString(PROFILE_KEY);
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            CustomFactory factory = new CustomFactory(storage, mUsername);
            mBasicViewModel = ViewModelProviders.of(this, factory).get(UserProjectsViewModel.class);
        }
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ProjectsBinding binding = ProjectsBinding.inflate(inflater, container, false);
//        binding.setVm(mUserProjectsViewModel);
//        binding.setLifecycleOwner(this);
//        return binding.getRoot();
//    }

}
