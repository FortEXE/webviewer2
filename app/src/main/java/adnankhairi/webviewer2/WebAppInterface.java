package adnankhairi.webviewer2;

import android.content.Context;
import android.content.Intent;
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
    public boolean play(String vidio){
        if(vidio.contains("mp4")){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Eksekusi link sebagai video
            intent.setDataAndType(Uri.parse(vidio), "video/mp4");
            context.startActivity(intent);
            Log.d("OWO",vidio);
            return true;
        }else{
            Log.d("OWO",vidio);
            return false;
        }
    }
}
