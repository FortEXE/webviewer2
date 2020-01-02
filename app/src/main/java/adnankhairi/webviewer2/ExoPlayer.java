package adnankhairi.webviewer2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class ExoPlayer extends AppCompatActivity{
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    public static String url;
    public Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);

        final String video = getIntent().getStringExtra("video");

        iniExoPlayer(video);
    }

    public void iniExoPlayer(String url){
        Log.d("owo",url);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView = findViewById(R.id.video_player);
        playerView.setPlayer(simpleExoPlayer);

        //#Default Data Source use this
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "app-name"));
        MediaSource videoSource = new SsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));

        //#Other Data Source use this
        //DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Util.getUserAgent(this,"appname"));
        //MediaSource videoSource = new SsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        //DataSource.Factory dataSourceFactory = new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(this));

        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        simpleExoPlayer.release();
    }

}
