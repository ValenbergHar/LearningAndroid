package by.carkva_gazeta.resources;

import android.animation.ObjectAnimator;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

public class MyWebViewClient extends WebViewClient {

    private OnLinkListenner onLinkListenner;

    void setOnLinkListenner(OnLinkListenner onLinkListenner) {
        this.onLinkListenner = onLinkListenner;
    }

    interface OnLinkListenner {
        void onActivityStart();

        void onDialogStart(String message);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, @NonNull String url) {
        if (url.contains("https://m.carkva-gazeta.by/index.php?toUp=1")) {
            ObjectAnimator anim = ObjectAnimator.ofInt(view, "scrollY", view.getScrollY(), 0);
            anim.setDuration(1500).start();
        }
        if (url.contains("https://m.carkva-gazeta.by/index.php?Alert=")) {
            int t1 = url.lastIndexOf("=");
            String message = url.substring(t1 + 1);
            if (onLinkListenner != null)
                onLinkListenner.onDialogStart(message);
        }
        if (url.contains("https://m.carkva-gazeta.by/index.php?Activity=1")) {
            if (onLinkListenner != null)
                onLinkListenner.onActivityStart();
        }
        return true;
    }
}
