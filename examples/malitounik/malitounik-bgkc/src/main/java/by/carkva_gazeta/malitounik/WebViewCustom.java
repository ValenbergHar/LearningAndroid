package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

public class WebViewCustom extends WebView {

    private OnScrollChangedCallback mOnScrollChangedCallback;
    private OnBottomListener mListener;

    public WebViewCustom(Context context) {
        super(getFixedContext(context));
    }

    public WebViewCustom(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public WebViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        int diff = getContentHeight() - (int) ((getHeight() + getScrollY()) / getResources().getDisplayMetrics().density);

        if (mListener != null && diff == 0) {
            mListener.onBottom();
        }
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(t);
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public void setOnBottomListener(OnBottomListener onBottomListener) {
        mListener = onBottomListener;
    }

    public interface OnScrollChangedCallback {
        void onScroll(int t);
    }

    public interface OnBottomListener {
        void onBottom();
    }

    @SuppressLint("NewApi")
    private static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            return context.createConfigurationContext(new Configuration());
        } else {
            return context;
        }
    }
}
