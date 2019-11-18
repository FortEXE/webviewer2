package adnankhairi.webviewer2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;


public class WebAppInterface { //Class untuk mengintegrasikan JS dengan Java

    private Context context;

    public  WebAppInterface(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public void show(String vidio){
            PackageManager pm = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW); //Inisiasi intent
            intent.setDataAndType(Uri.parse(vidio), "video/*"); //Memungkinkan mengekusi link sebagai video
            //intent.setPackage("com.google.android.exoplayer"); //Memungkinkan play video dengan MX Player
            intent.setPackage("org.videolan.vlc"); //Memungkinkan play video dengan VLC for Android
            context.startActivity(intent); //Menjalankan intent
    }

    @JavascriptInterface
    public int openLiveTV(int channel){ //Cek ada atau tidaknya channel
        String chanel = String.valueOf(channel);
        Log.d("channel",chanel);
        return channel;
    }

    @JavascriptInterface
    public void getIndihomeId(){ //Jangan dihapus

    }

//    @JavascriptInterface
//    public void show(String log){
//        Log.d("channel",log);
//    }
}
