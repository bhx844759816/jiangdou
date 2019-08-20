package com.jxqm.jiangdou.view.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import com.haibin.calendarview.WeekBar;
import com.jxqm.jiangdou.R;

/**
 * Created By bhx On 2019/8/19 0019 10:17
 */
public class SingleWeekBar extends WeekBar {
    public SingleWeekBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_single_week_bar, this, true);
    }

}
