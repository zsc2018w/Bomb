package com.bomb.common.basic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseF extends Fragment {

    protected View fView;


    protected Boolean pageLoad = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVm();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            if (fView == null) {
                fView = inflater.inflate(getLayoutId(), container, false);
                initView();
            }
            ViewGroup parent = (ViewGroup) fView.getParent();
            if (parent != null) {
                parent.removeView(fView);
            }
            return fView;

        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected int getLayoutId() {
        return 0;
    }


    protected void initVm() {

    }

    abstract void initView();


    protected void showLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Bomb_T", getClass().getSimpleName()+"--->onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Bomb_T", getClass().getSimpleName()+"--->onPause");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("Bomb_T", getClass().getSimpleName()+"--->onHiddenChanged: --->"+hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Bomb_T", getClass().getSimpleName()+"--->setUserVisibleHint:---> "+isVisibleToUser);
    }

    protected void closeLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).closeLoading();
        }
    }

}
