package com.reemsib.shadowreader.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.*
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.reemsib.shadowreader.R
import com.reemsib.shadowreader.adapter.RecordingAdapter
import com.reemsib.shadowreader.model.Recording
import com.reemsib.shadowreader.utils.Config
import kotlinx.android.synthetic.main.activity_paragraph_detail.*
import java.io.File
import java.io.IOException
import java.util.*


class ParagraphDetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, View.OnClickListener{
    private val RECOVERY_REQUEST = 1
    private var youTubeView: YouTubePlayerView? = null
    var video: String? = null
    private var lesson: String? = null
    private var paragraph: String? = null
    private var videoId: Int? = null

    private val RECORD_AUDIO_REQUEST_CODE = 123

    private var mRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var fileName: String? = null
    private var lastProgress = 0
    private val mHandler: Handler = Handler()
    private var isPlaying = false

    private val recordingList: ArrayList<Recording>? = null
    private var recordingAdapter: RecordingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paragraph_detail)

      val i=intent
        if(i != null){
            tv_lessonName.text=i.getStringExtra("lesson")
            tv_paragraphNum.text=i.getStringExtra("title")
            video=i.getStringExtra("video")

            videoId = i.getIntExtra("video_id", -1)
            lesson = i.getStringExtra("lesson")
            paragraph = i.getStringExtra("title")

            Log.e("lesson_video", lesson + "" + paragraph + "")
        }

        youTubeView =  findViewById(R.id.youtube_view) as (YouTubePlayerView)

        youTubeView!!.initialize(Config.YOUTUBE_API_KEY, this)

     //   rv_records.layoutManager=LinearLayoutManager(applicationContext)
      //  rv_records.setHasFixedSize(true)


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            getPermissionToRecordAudio()
        }
     //   fetchRecordings()

        ic_back.setOnClickListener(this)
        img_recordVoice.setOnClickListener(this)
        imgPause.setOnClickListener(this)
        imgPlay.setOnClickListener(this)


        }
    private fun fetchRecordings() {
        val root:File = Environment.getExternalStorageDirectory()
        val path:String = root.absolutePath + "/VoiceRecorderSimplifiedCoding/Audios"
        Log.d("Files", "Path: $path")
        val directory:File = File(path)
        val files:Array<File> = directory.listFiles()!!
     //   Log.d("Files", "Size: " + files.size)

        if (files.isNotEmpty()) {
            Log.d("Files", "Size: " + files.size)
        }
        if (files.isNotEmpty()) {
            for (i in files.indices) {
                Log.d("Files", "FileName:" + files[i].name)
                val fileName = files[i].name
                val recordingUri =
                    root.absolutePath + "/VoiceRecorderSimplifiedCoding/Audios/" + fileName
                val recording = Recording(recordingUri, fileName, false)
                recordingList!!.add(recording)
            }
        //    textViewNoRecordings.setVisibility(View.GONE)
         //   recyclerViewRecordings.setVisibility(View.VISIBLE)
            setAdaptertoRecyclerView()
        } else {
         //   textViewNoRecordings.setVisibility(View.VISIBLE)
         //   recyclerViewRecordings.setVisibility(View.GONE)
        }
    }

    private fun setAdaptertoRecyclerView() {
        recordingAdapter = RecordingAdapter(this, recordingList)
      //  rv_records.setAdapter(recordingAdapter)
    }

    override fun onClick(p0: View?) {
       when(p0!!.id){
           R.id.img_recordVoice -> {
               prepareforRecording()
               startRecording()
               Timer().schedule(object : TimerTask() {
                   override fun run() {
                       runOnUiThread {
                           prepareforStop()
                           stopRecording()
                       }
                   }
               }, 120000) //<-- Execute code after 15000 ms i.e after 15 Seconds.

           }
           R.id.imgPause -> {
               prepareforStop()
               stopRecording()
           }
           R.id.imgPlay -> {
               if (!isPlaying && fileName != null) {
                   isPlaying = true
                   startPlaying()
               } else {
                   isPlaying = false
                   stopPlaying()
               }
           }
           R.id.ic_back -> {
               finish()
           }
       }
    }

    private fun prepareforRecording() {
        TransitionManager.beginDelayedTransition(linear_record)
        img_recordVoice.visibility=View.GONE
        imgPause.visibility=View.VISIBLE
        linear_play.visibility = View.GONE

    }

    private fun startRecording() {
        //we use the MediaRecorder class to record
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
         * and the audios are being stored in the Audios folder  */
        val root = Environment.getExternalStorageDirectory()
        val file = File(root.absolutePath + "/ShadowReader/Audios/" + lesson + "/" + paragraph + "/")
        if (!file.exists()) {
            file.mkdirs()
        }
        fileName = root.absolutePath + "/ShadowReader/Audios/"+lesson+"/"+paragraph+"/" + (System.currentTimeMillis().toString() + ".mp3")
        Log.d("filename", fileName!!)
        mRecorder!!.setOutputFile(fileName)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            mRecorder!!.prepare()
            mRecorder!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        lastProgress = 0
        seekbar.setProgress(0)
        seekBar_record.setProgress(0)
        stopPlaying()
        //starting the chronometer
        chronometerTimer.setBase(SystemClock.elapsedRealtime())
        chronometerTimer.start()
    }

    private fun stopPlaying() {
        try {
            mPlayer!!.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mPlayer = null
        //showing the play button
        imgPlay.setImageResource(R.drawable.ic_play)
        chronometerTimer.stop()
    }

    private fun prepareforStop() {
        TransitionManager.beginDelayedTransition(linear_record)
        img_recordVoice.setVisibility(View.VISIBLE)
        imgPause.setVisibility(View.GONE)
        linear_play.setVisibility(View.VISIBLE)


    }

    private fun stopRecording() {
        try {
            mRecorder!!.stop()
            mRecorder!!.release()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        mRecorder = null
        //starting the chronometer
        chronometerTimer.stop()
        chronometerTimer.setBase(SystemClock.elapsedRealtime())
        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show()
    }

    private fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
          //fileName is global string. it contains the Uri to the recently recorded audio.
            mPlayer!!.setDataSource(fileName)
            mPlayer!!.prepare()
            mPlayer!!.start()
        } catch (e: IOException) {
            Log.e("LOG_TAG", "prepare() failed")
        }
        //making the imageview pause button
        imgPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        seekbar.setProgress(lastProgress)
        seekBar_record.setProgress(lastProgress)
        mPlayer!!.seekTo(lastProgress)
        seekbar.setMax(mPlayer!!.duration)
        seekBar_record.setMax(mPlayer!!.duration)
        seekUpdation()
        chronometerTimer.start()
        /** once the audio is complete, timer is stopped here */
        mPlayer!!.setOnCompletionListener {
            imgPlay.setImageResource(R.drawable.ic_play)
            isPlaying = false
            chronometerTimer.stop()
        }
        /** moving the track as per the seekBar's position */
        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mPlayer != null && fromUser) {
                    //here the track's progress is being changed as per the progress bar
                    mPlayer!!.seekTo(progress)
                    //timer is being updated as per the progress of the seekbar
                    chronometerTimer.setBase(SystemClock.elapsedRealtime() - mPlayer!!.currentPosition)
                    lastProgress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    var runnable = Runnable { seekUpdation() }

    private fun seekUpdation() {
        if (mPlayer != null) {
            val mCurrentPosition = mPlayer!!.currentPosition
            seekbar.setProgress(mCurrentPosition)
            seekBar_record.setProgress(mCurrentPosition)
            lastProgress = mCurrentPosition
        }
        mHandler.postDelayed(runnable, 100)
    }

    override fun onInitializationSuccess(
        Provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {

        if (!wasRestored) {
            player!!.loadVideo(video!!) // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            //player.play()
         }
     //   player!!.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
        if (errorReason!!.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
            val error =
                java.lang.String.format(getString(R.string.player_error), errorReason.toString())
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this)
        }
    }

    protected fun getYouTubePlayerProvider(): YouTubePlayer.Provider {
        return youTubeView!!
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                    this,
                    "You must give permissions to use this app. App is exiting.",
                    Toast.LENGTH_SHORT
                ).show()
                finishAffinity()
            }
        }
    }


}
