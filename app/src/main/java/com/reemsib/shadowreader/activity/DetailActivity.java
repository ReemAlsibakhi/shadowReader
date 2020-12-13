package com.reemsib.shadowreader.activity;
import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.reemsib.shadowreader.R;
import com.reemsib.shadowreader.adapter.RecordingAdapter;
import com.reemsib.shadowreader.model.Recording;
import com.reemsib.shadowreader.setting.MySingleton;
import com.reemsib.shadowreader.setting.PreferencesManager;
import com.reemsib.shadowreader.utils.BaseActivity;
import com.reemsib.shadowreader.utils.GlobalService;
import com.reemsib.shadowreader.utils.URLs;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import cz.msebera.android.httpclient.Header;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private int RECORD_AUDIO_REQUEST_CODE = 123;
    private Chronometer chronometer;
    private ImageView imageViewRecord, imageViewPlay, imageViewStop, imageSeekPlay;
    private RelativeLayout relativeRecord;
    private SeekBar seekBar;
    private SeekBar seekBar_record;
    private LinearLayout linearLayoutRecorder, linearLayoutPlay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String fileName = null;
    private int lastProgress = 0;
    private int lastProgress1 = 0;
    private Handler mHandler = new Handler();
    private boolean isPlaying = false;
    private boolean is_send_voice = false;
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Recording> recordingArraylist;
    private RecordingAdapter recordingAdapter;
    private TextView textViewNoRecordings;
    private TextView tvHaveSentRecord;
    private TextView PragName;
    private TextView lessonName;
    private String video;
    private String lesson;
    private String paragraph;
    private Integer videoId;
    private Button btn_ok;
    private String recordingUri;
    private String isShow;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayerView;
    private PreferencesManager manager;
    private File file;
    private AlertDialog myDialog;
    private ImageButton btnBack;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public static final String LOG_TAG_S = "MyService:";

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragraph_detail);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getPermissionToRecordAudio();
//        }
//

        initViews();
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        Intent i = getIntent();
        if (i != null) {
            video = i.getStringExtra("video");
            videoId = i.getIntExtra("video_id", -1);
            lesson = i.getStringExtra("lesson");
            paragraph = i.getStringExtra("title");
            lessonName.setText(lesson);
            PragName.setText(paragraph);
            Log.e("lesson_video", lesson + "" + paragraph + "");
        }
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(video, 0);
            }
            @Override
            public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError error) {
                super.onError(youTubePlayer, error);
               // String videoId = "S0Q4gqBUs7c";
                youTubePlayer.loadVideo(video, 0);
                Log.e("video",error.toString());
            }
        });

      showVideo(videoId);
    }

    private void initViews() {
        recordingArraylist = new ArrayList<Recording>();
        textViewNoRecordings = (TextView) findViewById(R.id.textViewNoRecordings);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        linearLayoutRecorder = (LinearLayout) findViewById(R.id.linear_record);
        relativeRecord = (RelativeLayout) findViewById(R.id.relative_record);
        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        imageViewRecord = (ImageView) findViewById(R.id.img_recordVoice);
        imageViewStop = (ImageView) findViewById(R.id.imgPause);
        imageViewPlay = (ImageView) findViewById(R.id.imgPlay);
        imageSeekPlay = (ImageView) findViewById(R.id.imagePlay);
        linearLayoutPlay = (LinearLayout) findViewById(R.id.linear_play);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar_record = (SeekBar) findViewById(R.id.seekBar_record);
        lessonName = (TextView) findViewById(R.id.tv_lessonName);
        PragName = (TextView) findViewById(R.id.tv_paragraphNum);
        tvHaveSentRecord = (TextView) findViewById(R.id.tv_haveSent);
        manager= new PreferencesManager(getApplicationContext());
        myDialog= BaseActivity.loading(DetailActivity.this);
        btnBack=(ImageButton)findViewById(R.id.ic_back);

        imageViewRecord.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        imageSeekPlay.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }
    private void fetchRecording() {
        File root = android.os.Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/ShadowReader/Audios/" + lesson + "/" + paragraph;
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            Log.d("Files", "Size: " + files.length);

        } else {
            //   Log.d("Files Null", "Size: "+ files.length);

        }
        if (files != null) {
            String fileName = files[files.length - 1].getName();
            recordingUri = root.getAbsolutePath() + "/ShadowReader/Audios/" + lesson + "/" + paragraph + "/" + fileName;
            relativeRecord.setVisibility(View.VISIBLE);

            Log.d("recordingUri", recordingUri);
//            mRecorder.setOutputFile(recordingUri);
//            for (int i = 0; i < files.length; i++) {
//
//                Log.d("Files", "FileName:" + files[i].getName());
//                String fileName = files[i].getName();
//                String recordingUri = root.getAbsolutePath() + "/ShadowReader/Audios/"+lesson+"/"+paragraph+"/"+fileName;
//                Log.e("recordingUri_1",recordingUri);
//                Log.e("fileName_1",fileName);
//                Recording recording = new Recording(recordingUri,fileName,false);
//                recordingArraylist.add(recording);
//            }

//            textViewNoRecordings.setVisibility(View.GONE);
//            recyclerViewRecordings.setVisibility(View.VISIBLE);
//            setAdaptertoRecyclerView();

        } else {
//            textViewNoRecordings.setVisibility(View.VISIBLE);
//            recyclerViewRecordings.setVisibility(View.GONE);
        }

    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new RecordingAdapter(this, recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
        recordingAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }

    // Callback with the request from calling requestPermissions(...)
  //  @RequiresApi(api = Build.VERSION_CODES.M)
    //@Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        // Make sure it's our original READ_CONTACTS request
//        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
//            if (grantResults.length == 3 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//
//                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
//
//            } else {
//            //    Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
//                finishAffinity();
//            }
//        }
//
//    }


    @Override
    public void onClick(View view) {
        if (view == imageViewRecord) {
            checkInternetState();
        } else if (view == imageViewStop) {
            prepareforStop();
            stopRecording();
        } else if (view == imageViewPlay) {
            if (!isPlaying && fileName != null) {
                isPlaying = true;
                startPlaying();
            } else {
                isPlaying = false;
                stopPlaying();
            }
        } else if (view == imageSeekPlay) {
            if (!isPlaying && fileName != null) {
                isPlaying = true;
                startPlaying1();
            } else {
                isPlaying = false;
                stopPlaying1();
            }

        }else if(view==btnBack){
            finish();
        }

    }

    private void checkInternetState() {
        if (isNetworkConnected()) {
           if (manager.isLoggedIn()){
               showVideo(videoId);
               if (!is_send_voice) {
                   prepareforRecording();
                       startRecording();
                       new Timer().schedule(new TimerTask() {
                       @Override
                       public void run() {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                 if (mRecorder != null){
                                     prepareforStop();
                                     stopRecording();
                                 }

                               }
                           });
                       }
                   }, 120000); //<-- Execute code after 15000 ms i.e after 15 Seconds.
               }
           }else {
               Intent i=new Intent(this,LoginActivity.class);
               startActivity(i);
           }

        } else {
            showAlertDialogInternet();
        }
    }

    private void startPlaying1() {
        mPlayer = new MediaPlayer();

        Log.d("instartPlaying", fileName);
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }

        //making the imageview pause button
        imageSeekPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        seekBar_record.setProgress(lastProgress1);
        mPlayer.seekTo(lastProgress1);
        isPlaying = true;
        seekBar_record.setMax(mPlayer.getDuration());
        seekUpdation1();
        chronometer.start();
        /** once the audio is complete, timer is stopped here**/
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageSeekPlay.setImageResource(R.drawable.ic_play);
                isPlaying = false;
                chronometer.stop();
            }
        });
        /** moving the track as per the seekBar's position**/
        seekBar_record.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    //here the track's progress is being changed as per the progress bar
                    mPlayer.seekTo(progress);
                    //timer is being updated as per the progress of the seekbar
                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress1 = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void stopPlaying1() {
        try {
            mPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer = null;
        isPlaying = false;
        //showing the play button
        imageSeekPlay.setImageResource(R.drawable.ic_play);

        chronometer.stop();
    }

    private void prepareforRecording() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.GONE);
        imageViewStop.setVisibility(View.VISIBLE);
        linearLayoutPlay.setVisibility(View.GONE);
        relativeRecord.setVisibility(View.GONE);
    }

    private void startRecording() {
         mRecorder = new MediaRecorder();
        //android.permission.MODIFY_AUDIO_SETTINGS
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //turn on speaker
        if (mAudioManager != null) {
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION); //MODE_IN_COMMUNICATION | MODE_IN_CALL
            // mAudioManager.setSpeakerphoneOn(true);
            // mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0); // increase Volume
            hasWiredHeadset(mAudioManager);
        }

        //android.permission.RECORD_AUDIO
        String manufacturer = Build.MANUFACTURER;
        Log.d(LOG_TAG_S, manufacturer);
           /* if (manufacturer.toLowerCase().contains("samsung")) {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            } else {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            }*/
            /*
            VOICE_CALL is the actual call data being sent in a call, up and down (so your side and their side). VOICE_COMMUNICATION is just the microphone, but with codecs and echo cancellation turned on for good voice quality.
            */

         mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
         mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
         File root = Environment.getExternalStorageDirectory();
        mRecorder.setAudioChannels(1);

        file = new File(root.getAbsolutePath() + "/ShadowReader/Audios/" + lesson + "/" + paragraph + "/");
          if (!file.exists()) {
            file.mkdirs();
        }
        fileName = root.getAbsolutePath() + "/ShadowReader/Audios/" + lesson + "/" + paragraph + "/" + String.valueOf(System.currentTimeMillis() + ".mp3");
        Log.d("filename", fileName);
        Log.d("file_", new File(fileName)+"");
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
   //     seekBar.setProgress(0);
        seekBar_record.setProgress(0);
        stopPlaying();
        //starting the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    private void stopPlaying() {
        try {
            mPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer = null;
        //showing the play button
        imageViewPlay.setImageResource(R.drawable.ic_play);

        chronometer.stop();

    }

    private void prepareforStop() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.GONE);
        linearLayoutPlay.setVisibility(View.VISIBLE);
        relativeRecord.setVisibility(View.VISIBLE);
    }

    private void stopRecording() {

        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        //showing the play button
      //  Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
        Log.e("recording_saved","Recording saved successfully.");

        showAlertConfirmSend();
    }

    private void showAlertConfirmSend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage("Are you sure send your recording to your teacher ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        uploadVoiceFile();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        Log.d("instartPlaying", fileName);
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        imageViewPlay.setImageResource(R.drawable.ic_baseline_pause_24);

        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);
        seekBar.setMax(mPlayer.getDuration());
        seekUpdation();
        chronometer.start();

        /** once the audio is complete, timer is stopped here**/
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageViewPlay.setImageResource(R.drawable.ic_play);
                isPlaying = false;
                chronometer.stop();
            }
        });

        /** moving the track as per the seekBar's position**/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    //here the track's progress is being changed as per the progress bar
                    mPlayer.seekTo(progress);
                    //timer is being updated as per the progress of the seekbar
                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //  seekUpdation();
            seekUpdation1();
        }
    };

    private void seekUpdation() {
        if (mPlayer != null) {
            int mCurrentPosition = mPlayer.getCurrentPosition();
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }

    private void seekUpdation1() {
        if (mPlayer != null) {
            int mCurrentPosition = mPlayer.getCurrentPosition();
            seekBar_record.setProgress(mCurrentPosition);
            lastProgress1 = mCurrentPosition;
        }
        mHandler.postDelayed(runnable, 100);
    }


    private void showVideo(Integer videoId) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_SHOW_VIDEO + videoId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    //if no error in response
                    if (obj.getBoolean("status")) {

                        JSONObject videoJson = obj.getJSONObject("video");

                        is_send_voice = videoJson.getBoolean("is_send_voice");

                        if (is_send_voice) {
                            Log.e("is_send_voice_1", is_send_voice + "");
                            TransitionManager.beginDelayedTransition(linearLayoutRecorder);
                            linearLayoutRecorder.setVisibility(View.GONE);
                            tvHaveSentRecord.setVisibility(View.VISIBLE);
                        } else {
                            Log.e("is_send_voice_2", is_send_voice + "");
                            linearLayoutRecorder.setVisibility(View.VISIBLE);
                            tvHaveSentRecord.setVisibility(View.GONE);
                        }


                    } else {
                     //   Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accept","application/json");
                params.put("Accept-Language","en");
                params.put("Authorization","Bearer "+new PreferencesManager(getApplicationContext()).gettAccessToken());
                return params;
            }
        };
        MySingleton.Companion.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showAlertDialogInternet() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.dialog_connect_internet, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(mDialogView);
        TextView message = mDialogView.findViewById(R.id.tv_msg);
        message.setText("please connect to the internet");
        mDialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showAlertSendVoice() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.dialog_connect_internet, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        dialog.setView(mDialogView);
        TextView message = mDialogView.findViewById(R.id.tv_msg);
        message.setText("You have already sent an audio clip for this paragraph");
        mDialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showThankDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.dialog_thank_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(mDialogView);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        mDialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    BroadcastReceiver messageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                video = bundle.getString("videoId");
                String po = bundle.getString("Positives");
                assert video != null;
                Log.e("video", video);
                assert po != null;
                Log.e("notif_aaaaaa", po);
            //    Toast.makeText(getApplicationContext(), bundle.getString(bundle.getString("Positives")), Toast.LENGTH_LONG).show();

            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("notify"));

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);

    }

    public void uploadVoiceFile() {
        myDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("accept", "application/json");
        client.addHeader("Accept-Language", "en");
        client.addHeader("Authorization", "Bearer " + new PreferencesManager(getApplicationContext()).gettAccessToken());
        Log.e("Authorization", new PreferencesManager(getApplicationContext()).gettAccessToken());
        client.setConnectTimeout(10*1000*60);
        client.setResponseTimeout(10*1000*60);
        RequestParams params = new RequestParams();
        params.put("video_id", videoId);
        params.put("fcm_token", new PreferencesManager(getApplicationContext()).gettFcmToken());
        try {
            params.put("voice",new File(fileName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("object", params.toString());

        client.post(URLs.URL_SEND_VOICE, params, new JsonHttpResponseHandler() {

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("joinUs", response.toString());
                myDialog.hide();
                try {
                    String status = response.getString("status");

                    if (status.equals("true")) {
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                        Log.e("SUCCESS_UPLOAD", "success_upload");
                        showThankDialog();
                        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
                        linearLayoutRecorder.setVisibility(View.GONE);
                        tvHaveSentRecord.setVisibility(View.VISIBLE);
                    } else {
                        myDialog.hide();
                        String message = response.getString("msg");
                        Log.e("failed_upload", message);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace(); }
            }
            @Override
            public void onFinish() {
                super.onFinish();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                myDialog.hide();
             //   Toast.makeText(getApplicationContext(),errorResponse.toString(),Toast.LENGTH_LONG).show();
                Log.e("errorResponse", errorResponse.toString() + "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
              //  Toast.makeText(getApplicationContext(),errorResponse.toString(),Toast.LENGTH_LONG).show();
                Log.e("errorResponse", errorResponse.toString() + "");
                myDialog.hide();

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", responseString.toString() + "");
              // Toast.makeText(getApplicationContext(),responseString.toString(),Toast.LENGTH_LONG).show();
                myDialog.hide();

            }


            @Override
            public void onUserException(Throwable error) {
                //super.onUserException(error);
            }
        });
    }
    // To detect the connected other device like headphone, wifi headphone, usb headphone etc
    private boolean hasWiredHeadset(AudioManager mAudioManager) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return mAudioManager.isWiredHeadsetOn();
        } else {
            @SuppressLint("WrongConstant") final AudioDeviceInfo[] devices = mAudioManager.getDevices(AudioManager.GET_DEVICES_ALL);
            for (AudioDeviceInfo device : devices) {
                final int type = device.getType();
                if (type == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                    Log.d(LOG_TAG_S, "hasWiredHeadset: found wired headset");
                    return true;
                } else if (type == AudioDeviceInfo.TYPE_USB_DEVICE) {
                    Log.d(LOG_TAG_S, "hasWiredHeadset: found USB audio device");
                    return true;
                } else if (type == AudioDeviceInfo.TYPE_TELEPHONY) {
                    Log.d(LOG_TAG_S, "hasWiredHeadset: found audio signals over the telephony network");
                    return true;
                }
            }
            return false;
        }
    }
    }



