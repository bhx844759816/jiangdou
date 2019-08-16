package com.bhx.common.selectcity.callback;

import com.bhx.common.selectcity.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
