/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.jddeep.youtubeplayer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    YouTubePlayerView youTubePlayerView;
    boolean isPlaying = true;
    static float currTime = 0.0f;
    float vidDuration = currTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_player);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
//                YouTubePlayerUtils.loadOrCueVideo(youTubePlayer,
//                        this,"S0Q4gqBUs7c",0f
//                        );
                youTubePlayer.loadVideo("S0Q4gqBUs7c", 0f);
            }
        });
        youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {
                currTime = v;
            }

            @Override
            public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {
                vidDuration = v;
                Log.e("VideoDuration: ", ""+ vidDuration);
            }

            @Override
            public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(YouTubePlayer youTubePlayer, String s) {

            }

            @Override
            public void onApiChange(YouTubePlayer youTubePlayer) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
            @Override
            public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.pause();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("dispatchKeyEvent: ", "keycode: " + event.getKeyCode() + "event: " + event.toString());
        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if(event.getAction() == 0)
                    youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                        @Override
                        public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                            if(!isPlaying){
                                youTubePlayer.play();
                                Log.e("Play: ", String.valueOf(isPlaying));
                                isPlaying = true;
                            }else {
                                youTubePlayer.pause();
                                Log.e("Pause: ", String.valueOf(isPlaying));
                                isPlaying = false;
                            }
                        }
                    });
                return true;
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                if(event.getAction() == 0)
                youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                    @Override
                    public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                        currTime += 15.0f;
                        if(currTime >= vidDuration) return;
//                            currTime = vidDuration;
                        Log.e("FRWDCURRTime: ", ""+currTime);
                        youTubePlayer.seekTo(currTime);

                    }
                });
                return true;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                if(event.getAction() == 0)
                youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                    @Override
                    public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                        if(currTime >= 15.0f)
                        currTime -= 15.0f;
                        else currTime = 0.0f;
                        youTubePlayer.seekTo(currTime);

                    }
                });
                return true;

        }
        return true;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e("onKeyDown: ", "keycode: " + keyCode + "event: " + event.toString());
//        switch (keyCode){
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                if(event.getAction() == 0)
//                youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
//                    @Override
//                    public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
//                        if(!isPlaying){
//                            youTubePlayer.play();
//                            Log.e("Play: ", String.valueOf(isPlaying));
//                            isPlaying = true;
//                        }else {
//                            youTubePlayer.pause();
//                            Log.e("Pause: ", String.valueOf(isPlaying));
//                            isPlaying = false;
//                        }
//                    }
//                });
//                return true;
//            case KeyEvent.KEYCODE_DPAD_UP_RIGHT:
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
//                    @Override
//                    public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
//                        youTubePlayer.seekTo(5.0f);
//                    }
//                });
//                return true;
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
