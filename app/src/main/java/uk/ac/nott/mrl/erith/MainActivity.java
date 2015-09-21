package uk.ac.nott.mrl.erith;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.PermissionRequest;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private WebView myWebView;
    private View mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        WindowManager manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50 * getResources().getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        setContentView(R.layout.activity_main);
        customViewGroup view = new customViewGroup(this);
        manager.addView(view, localLayoutParams);

        /*final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        final View decorView = getWindow().getDecorView();
        mMainView = decorView.findViewById(android.R.id.content);
        */
        //mMainView.setSystemUiVisibility(flags);

       /* mMainView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                System.out.println("AM HERE!!!");
                if ((visibility & mMainView.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    System.out.println("AM HERE TOO!!!!!");
                    mMainView.setSystemUiVisibility(flags);
                }
            }
        });*/
        myWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //myWebView.clearCache(true);
        myWebView.setWebViewClient(new WebC());
        //myWebView.addView(view, localLayoutParams);
        myWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "-----------------------------------onPermissionRequest");
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });

            }

            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("My Application", message + "--- from line "
                        + lineNumber + " of "
                        + sourceID);
            }

        });

        myWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        myWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        myWebView.setLongClickable(false);

        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.setHorizontalScrollBarEnabled(false);
        //http://tlodge.github.io/erith-react/
        myWebView.loadUrl("https://corman.mrl.nott.ac.uk/");
                //"http://tlodge.github.io/erith-react/client/");
                //"http://buttonkit.com/camera2.html");



    }

    @Override
    public void onBackPressed(){
        myWebView.loadUrl("https://corman.mrl.nott.ac.uk/");
    }

    //@Override
    //public void onAttachedToWindow() {
    //    getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
     //   super.onAttachedToWindow();
    //}

    public class WebC extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            return false;
        }
    }

    public class customViewGroup extends ViewGroup{
        public customViewGroup(Context context){
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b){

        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev){
            System.out.println("custom view interecpt!");
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
