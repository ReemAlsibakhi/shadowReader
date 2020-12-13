package com.reemsib.shadowreader.utils;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;

import androidx.core.app.NotificationCompat;

import com.reemsib.shadowreader.R;
import com.reemsib.shadowreader.activity.MainActivity;

import java.io.IOException;

public class GlobalService extends AccessibilityService {

    public static final String LOG_TAG_S = "MyService:";

    WindowManager windowManager;
    MediaRecorder recorder = new MediaRecorder();
    MediaPlayer player = new MediaPlayer();
    @SuppressLint("RtlHardcoded")
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Log.i("start Myservice","MyService");

        startForegroundService();
        //==============To Record Audio wile Call received=================
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.eventTypes=AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        info.packageNames = null;
        setServiceInfo(info);


        /*if (!Settings.canDrawOverlays(getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);
        }*/

      /*  back = new ImageView(this);
        home = new ImageView(this);
        minimize = new ImageView(this);
        notification = new ImageView(this);

        back.setImageResource(R.drawable.ic_launcher_background);
        home.setImageResource(R.drawable.ic_launcher_background);
        minimize.setImageResource(R.drawable.ic_launcher_background);
        notification.setImageResource(R.drawable.ic_launcher_background);
*/
  /*      params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 10;
        params.y = 50;

        windowManager.addView(home, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 10;
        params.y = 100;

        windowManager.addView(minimize, params);

        params= new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        params.x = 10;
        params.y = 150;

        windowManager.addView(notification, params);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
*/


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.e(LOG_TAG_S, "Event :"+event.getEventType());
    }

    @Override
    public void onInterrupt() {
    }


  /*  @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("TAG", "onServiceConnected");
    }*/


/*    @Override
    public void onServiceConnected() {
        // Set the type of events that this service wants to listen to. Others
        // won't be passed to this service.

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED;

        // If you only want this service to work with specific applications, set their
        // package names here. Otherwise, when the service is activated, it will listen
        // to events from all applications.
        info.packageNames = new String[]
                {"nisarg.app.demo"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated. This service *is*
        // application-specific, so the flag isn't necessary. If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        // info.flags = AccessibilityServiceInfo.DEFAULT;

        info.notificationTimeout = 100;

        this.setServiceInfo(info);


    }*/

    @Override
    protected void onServiceConnected() {
        System.out.println("onServiceConnected");

        //==============================Record Audio while  Call received===============//

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        FrameLayout layout = new FrameLayout(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|
                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS|
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP;

        windowManager.addView(layout, params);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //You can either get the information here or on onAccessibilityEvent



                Log.e(LOG_TAG_S, "Window view touched........:");
                Log.e(LOG_TAG_S, "Window view touched........:");
                return true;
            }
        });

        //==============To Record Audio wile Call received=================


        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.eventTypes=AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        info.notificationTimeout = 100;
        info.packageNames = null;
        setServiceInfo(info);



        try {
            //startRecording();
            startRecordingA();
        } catch (Exception e) {
            e.printStackTrace();
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over

                //stopRecording();
                stopRecordingA();
            }
        }, 30000);

    }
    private void startPlaying() {
        MediaPlayer player = new MediaPlayer();
        try {
       //     player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG_S, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecordingA() {
        // This must be needed sourcea
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
      //  recorder.setOutputFile(fileName);
        //recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
            recorder.setAudioEncodingBitRate(48000);
        } else {
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioEncodingBitRate(64000);
        }
        recorder.setAudioSamplingRate(16000);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG_S, "prepare() failed");
        }

        recorder.start();
    }
    private void stopRecordingA() {

        Log.e(LOG_TAG_S, "stop recording");
        recorder.stop();
        recorder.release();
        recorder = null;
    }
    public static final String CHANNEL_ID = "MyAccessibilityService";
    private void startForegroundService() {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,           0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentTitle("recording Service")
                .setContentText("Start")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Recording Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }



    //=================================================Added code start==========

    MediaRecorder mRecorder;
    private boolean isStarted;
    byte buffer[] = new byte[8916];



    public void startRecording() {
        try {

         /*   String timestamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.US).format(new Date());
            String fileName =timestamp+".3gp";
            mediaSaver = new MediaSaver(context).setParentDirectoryName("Accessibility").

                    setFileNameKeepOriginalExtension(fileName).
                    setExternal(MediaSaver.isExternalStorageReadable());*/
            //String selectedPath = Environment.getExternalStorageDirectory() + "/Testing";
            //String selectedPath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/Android/data/" + packageName + "/system_sound";



            mRecorder = new MediaRecorder();
//            mRecorder.reset();

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
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION); //MIC | VOICE_COMMUNICATION (Android 10 release) | VOICE_RECOGNITION | (VOICE_CALL = VOICE_UPLINK + VOICE_DOWNLINK)
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //THREE_GPP | MPEG_4
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); //AMR_NB | AAC
          //  mRecorder.setOutputFile(fileName);
            mRecorder.prepare();
            mRecorder.start();
            isStarted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (isStarted && mRecorder != null) {
            mRecorder.stop();
            mRecorder.reset(); // You can reuse the object by going back to setAudioSource() step
            mRecorder.release();
            mRecorder = null;
            isStarted = false;
        }
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



    //=================================End================================

    public  static  boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        //your package /   accesibility service path/class
        //
        // final String service = "com.example.sotsys_014.accessibilityexample/com.accessibilityexample.Service.MyAccessibilityService";
        final String service = "com.reemsib.shadowreader.utils/com.reemsib.shadowreader.utils.GlobalService";
        boolean accessibilityFound = true;
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(LOG_TAG_S, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(LOG_TAG_S, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(LOG_TAG_S, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();

                    Log.v(LOG_TAG_S, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        Log.v(LOG_TAG_S, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(LOG_TAG_S, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }

}