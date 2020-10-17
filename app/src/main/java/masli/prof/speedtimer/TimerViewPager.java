package masli.prof.speedtimer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class TimerViewPager extends ViewPager { // этот класс тупа чтобы не пекрелистывался лишний раз

    public static boolean enableSwipe = true;

    public TimerViewPager(@NonNull Context context) {
        super(context);
    }

    public TimerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return enableSwipe && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return enableSwipe && super.onTouchEvent(ev);
    }
}
