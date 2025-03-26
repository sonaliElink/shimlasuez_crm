package elink.suezShimla.water.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webview;
    String link = "";
    ImageView img_back, img_share;
    private ProgressDialog pDialog;
    private String removePdfTopIcon = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        // prevent ss and hide content when app is on background
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.lbl_please_wait));
        pDialog.setCancelable(false);

        if (getIntent() != null) {
            link = getIntent().getStringExtra("link");
        }

        img_back = findViewById(R.id.img_back);
        img_share = findViewById(R.id.img_share);
        webview = findViewById(R.id.webView);
        //showpDialog();
       // startWebView("https://docs.google.com/gview?embedded=true&url="+link);
        showPdfFile(link);
        img_share.setOnClickListener(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_share:

                try {
                    String url=link.replace("\\", "/");

                    Log.e("Link",url);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CRM");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "MCR Copy : \n\n"+url);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showpDialog();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onLoadResource(WebView view, String url) {
                //showpDialog();
            }

            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hidepDialog();

                    }
                }, 3000);
            }
        });
        webview.invalidate();
        webview.getSettings().setJavaScriptEnabled(false);//commented by sonali to false
        webview.getSettings().setSupportZoom(false);
        webview.loadUrl(url);
    }

    private void showPdfFile(final String imageString) {
        showpDialog();
        webview.invalidate();
        webview.getSettings().setJavaScriptEnabled(false);
        webview.getSettings().setSupportZoom(false);
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + imageString);
        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    webview.loadUrl(removePdfTopIcon);
                    hidepDialog();
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }
}