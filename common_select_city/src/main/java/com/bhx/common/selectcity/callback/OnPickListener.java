package com.bhx.common.selectcity.callback;


import com.bhx.common.selectcity.model.City;

public interface OnPickListener {
    void onPick(int position, City data);
    void onLocate();
    void onCancel();
}
