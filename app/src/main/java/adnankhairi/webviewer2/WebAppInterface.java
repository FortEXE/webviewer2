package adnankhairi.webviewer2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;

public class WebAppInterface {

    private Context context;

    public  WebAppInterface(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void play(String vidio){
            PackageManager pm = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(vidio), "video/*");
            intent.setPackage("com.mxtech.videoplayer.ad");
            context.startActivity(intent);
    }
}
