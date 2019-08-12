package com.bhx.common.mvvm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.bhx.common.utils.ReflexUtils;

/**
 * Created By bhx On 2019/8/6 0006 10:06
 */
public class BaseAndroidViewModel<T extends BaseRepository> extends AndroidViewModel {

    T mRepository;

    public BaseAndroidViewModel(Application application) {
        super(application);
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
