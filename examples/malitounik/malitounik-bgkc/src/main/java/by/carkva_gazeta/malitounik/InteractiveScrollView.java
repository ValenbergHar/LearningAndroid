package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by oleg on 9.11.16
 */

public class InteractiveScrollView extends ScrollView {

    private OnScrollChangedCallback mOnScrollChangedCallback;
    private OnBottomReachedListener mListener;
    private OnNestedTouchListener mTouch;

    public InteractiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InteractiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InteractiveScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY()));

        if (mListener != null && diff == 0) {
            mListener.onBottomReached();
        }
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(t);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouch != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouch.onTouch(true);
                    break;
                case MotionEvent.ACTION_UP:
                    mTouch.onTouch(false);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }

    public void setOnNestedTouchListener(OnNestedTouchListener mTouch) {
        this.mTouch = mTouch;
    }

    public interface OnScrollChangedCallback {
        void onScroll(int t);
    }

    public interface OnBottomReachedListener {
        void onBottomReached();
    }

    public interface OnNestedTouchListener {
        void onTouch(boolean action);
    }
}
