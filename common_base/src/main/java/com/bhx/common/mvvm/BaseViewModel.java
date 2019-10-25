package com.bhx.common.mvvm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.bhx.common.utils.ReflexUtils;

/**
 * BaseViewModel
 */
public class BaseViewModel<T extends BaseRepository> extends ViewModel {
    public T mRepository;

    public BaseViewModel() {
        mRepository = ReflexUtils.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
}
