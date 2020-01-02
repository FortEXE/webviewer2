// aplikasi web view
// adnan khairi & Ammar Ashshiddiqi
// email: adnankhairi@student.upi.edu, ammar.ashshiddiqi@student.upi.edu

package adnankhairi.webviewer2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import im.delight.android.webview.AdvancedWebView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    // inisialisasi web view dengan webview dan progress bar
    private AdvancedWebView webView;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";
    public static String PACKAGE_NAME;
    //JavascriptInterface JSInterface;
    private static final String[] perms = {android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.MODIFY_AUDIO_SETTINGS};
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PACKAGE_NAME = getApplicationContext().getPackageName();
        // mendapatkan id desain dari activity_main.xml
        webView = (AdvancedWebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        // menyiapkan web client untuk di luncurkan
        webView.setWebChromeClient(new myWebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view,progress);
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.setWebViewClient(new myWebclient());

        webView.addJavascriptInterface(new WebAppInterface(this), "stb"); //deklarasi javascript interface
        // pengaturan lanjut untuk penanganan bug
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.loadUrl("http://platform-epg.infrasvc.id/home/index"); //Masuk ke web
        WebSettings webSettings = webView.getSettings(); //Allow WebSettings
        webSettings.setJavaScriptEnabled(true); //Enable Javascript
        webSettings.setBuiltInZoomControls(true); //Enable zoom control
        webSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){ //Memberi Izin kamera dan mikrofon
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) { //Ketika tidak diizinkan
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //Mengetes result izin
        super.onActivityResult(requestCode, resultCode, data);
        webView.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }
    }

    public class myWebclient extends WebViewClient { //Prosedur yang dijalankan saat WebViewClient dipanggil
        @Override
        public void onPageFinished(WebView view, String url) { //ketika page selesai diload
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE); //matikan progress bar
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) { //ketika page mulai diload
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE); //munculkan progress bar
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { //ketika mengambil http response
            view.loadUrl(url); //load url
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
        public void onPermissionRequest(PermissionRequest request) { //Minta request izin mic dan kamera

            PermissionRequest myRequest = request;
            if (EasyPermissions.hasPermissions(MainActivity.this, perms)){ //jika diizinkan, pakai
                Toast.makeText(MainActivity.this, "Access granted", Toast.LENGTH_SHORT).show();
            } else { //jika tidak, munculkan warning
                EasyPermissions.requestPermissions(MainActivity.this, "We need permission for Microphone and Camera", 123, perms);
                webView.reload();
            }
            myRequest.grant(myRequest.getResources());
        }
    }

    // penggunaan tombol back untuk kembali ke page sebelumnya
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //Kembali ke halaman sebelumnya
        if((keyCode== KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
