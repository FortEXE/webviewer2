// aplikasi web view
// adnan khairi
// email: adnankhairi@student.upi.edu

package adnankhairi.webviewer2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    // inisialisasi web view dengan webview dan progress bar
    private WebView webView;
    private ProgressBar progressBar;
    private WebViewClient webViewClient;
    private String url;
    private static final String TAG = "MainActivity";
    //JavascriptInterface JSInterface;
    private static final String[] perms = {android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.MODIFY_AUDIO_SETTINGS};
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // mendapatkan id desain dari activity_main.xml
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        // menyiapkan web client untuk di luncurkan
        webView.setWebViewClient(new WebViewClient() {
//            //Membuat aplikasi default player Android untuk membuka Url video
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if(url.contains("mp4")){
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    //Eksekusi link sebagai video
//                    intent.setDataAndType(Uri.parse(url), "video/mp4");
//                    startActivity(intent);
//                    Log.d("OWO",url);
//                    return true;
//                }else{
//                    return false;
//                }
//            }
        });


        //while(webView;loadUrl("javascript:clickFunction()"))
        webView.setWebChromeClient(new myWebChromeClient(){

        });
        //



        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        // pengaturan lanjut untuk penanganan bug
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        // meluncurkan url yang dituju
        String videopath = "http://192.168.15.100/vidplayer"; //link ini menggunakan link localhost yang diakses menggunakan IP komputer yang terhubung ke WiFI
        webView.loadUrl(videopath);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }
    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // membiarkan Error SSL, sehingga browser dapat di load
        }
    }


    public class myWebChromeClient extends WebChromeClient{

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @AfterPermissionGranted(123)
        @Override
        public void onPermissionRequest(PermissionRequest request) {

            PermissionRequest myRequest = request;
            if (EasyPermissions.hasPermissions(MainActivity.this, perms)){
                Toast.makeText(MainActivity.this, "Access granted", Toast.LENGTH_SHORT).show();
            } else {
                EasyPermissions.requestPermissions(MainActivity.this, "We need permission for Microphone and Camera", 123, perms);
                webView.reload();
            }

            myRequest.grant(myRequest.getResources());

        }

    }


    // penggunaan tombol back untuk kembali ke page sebelumnya
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode== KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
