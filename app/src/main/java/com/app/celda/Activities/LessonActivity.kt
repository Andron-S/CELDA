package com.app.celda.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.celda.Model.CourseInfo
import com.app.celda.Post.APIService
import com.app.celda.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.course_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.regex.Pattern

class LessonActivity: YouTubeBaseActivity() {
    private val VIDEO_SAMPLE = "https://www.youtube.com/watch?v=UAdjRSHUoso"

    // warning key - AIzaSyBGmrORQ6b7wq0KgharkWRAnivaIM9VdR4
    // normal key - AIzaSyBxSQdYCL2Ii-n1uq14ns1uqUWM40dOVAg
    private val apiKey = "AIzaSyBGmrORQ6b7wq0KgharkWRAnivaIM9VdR4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lesson_screen_layout)
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytVideo)

        ytPlayer.initialize(apiKey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                // loadVideo - это код видео, надо сделать парсер ссылки ютуба, чтоб выуживал код видео
                player?.loadVideo("UAdjRSHUoso")
                player?.play()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "Video player Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }


        fun getYoutubeVideoFromUrl(inUrl: String): String? {
            if (inUrl.toLowerCase().contains("youtu")) {
                return inUrl.substring(inUrl.lastIndexOf("/") + 1)
            }
            val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
            val compilePattern = Pattern.compile(pattern)
            val matcher = compilePattern.matcher(inUrl)
            return if (matcher.find()) {
                matcher.group()
            } else null
        }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

        fun get(id: Int) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://mitiusxa.beget.tech")
                .build()

            val service = retrofit.create(APIService::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getInfoCourse(id)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {


                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()
                                    ?.string()
                            )
                        )
                        val info = Gson().fromJson<CourseInfo>(prettyJson)



                    } else if (response.code() == 401){
                        val refresh_response = service.refreshToken()



                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }

            }




        }


    }





//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME)
//        }
//
//        val controller: MediaController = MediaController(this)
//        vVideo.setMediaController(controller)

//    override fun onStart() {
//        super.onStart()
//
//        initializePlayer()
//    }
//
//    override fun onPause() {
//        super.onPause()
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        outState.putInt(PLAYBACK_TIME, vVideo.currentPosition)
//    }
//
//
//    private fun initializePlayer() {
//
//        val videoUri = getMedia(VIDEO_SAMPLE)
//        vVideo.setVideoURI(videoUri)
//
//        vVideo.setOnPreparedListener(object: MediaPlayer.OnPreparedListener{
//            override fun onPrepared(p0: MediaPlayer?) {
//                if (mCurrentPosition > 0) {
//                    vVideo.seekTo(mCurrentPosition)
//                } else {
//                    vVideo.seekTo(1)
//                }
//
//                vVideo.start()
//            }
//
//        })
//
//        vVideo.setOnCompletionListener(object: MediaPlayer.OnCompletionListener {
//            override fun onCompletion(p0: MediaPlayer?) {
//                Toast.makeText(applicationContext, "Completed", Toast.LENGTH_SHORT).show()
//                vVideo.seekTo(0)
//            }
//
//        })
//
//    }
//
//    private fun releasePlayer() {
//        vVideo.stopPlayback()
//    }
//
//
//    private fun getMedia(mediaName: String): Uri {
//        if (URLUtil.isValidUrl(mediaName)) {
//            return Uri.parse(mediaName)
//        } else {
//            return Uri.parse("android.resource://$packageName/raw/$mediaName")
//        }
//    }

