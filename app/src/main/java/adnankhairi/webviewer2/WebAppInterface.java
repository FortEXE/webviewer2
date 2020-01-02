package adnankhairi.webviewer2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface { //Class untuk mengintegrasikan JS dengan Java

    public Context context;
    //private static String vidio;
    public  WebAppInterface(Context context){
       this.context = context;
    }

    @JavascriptInterface
    public void show(String vidio){
        Log.d("owo",vidio);
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW); //Inisiasi intent
        intent.setDataAndType(Uri.parse(vidio), "video/*"); //Memungkinkan mengekusi link sebagai video
        //intent.setPackage("com.google.android.exoplayer"); //Memungkinkan play video dengan MX Player
        intent.setPackage("org.videolan.vlc"); //Memungkinkan play video dengan VLC for Android
        context.startActivity(intent); //Menjalankan intent
        //context.startActivity(new Intent(context, ExoPlayer.class).putExtra("video", vidio));

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

}
