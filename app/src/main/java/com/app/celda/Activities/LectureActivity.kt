package com.app.celda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.celda.Model.CourseInfo
import com.app.celda.Model.LectionModel
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
import kotlinx.android.synthetic.main.lection_item.*
import kotlinx.android.synthetic.main.selected_course_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.regex.Pattern

class LectureActivity : YouTubeBaseActivity() {
    private val apiKey = "AIzaSyBGmrORQ6b7wq0KgharkWRAnivaIM9VdR4"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lection_item)
        val past_intent = intent.getStringExtra("key")
        get(past_intent!!.toInt())

//        tvContent.text = "The apple is the pomaceous fruit of the apple tree, species Malus domestica in the rose family (Rosaceae). It is one of the most widely cultivated tree fruits, and the most widely known of the many members of genus Malus that are used by humans. Apples grow on small, deciduous trees. The tree originated in Central Asia, where its wild ancestor, Malus sieversii, is still found today. Apples have been grown for thousands of years in Asia and Europe, and were brought to North America by European colonists. Apples have been present in the mythology and religions of many cultures, including Norse, Greek and Christian traditions. In 2010, the fruit&apos;s genome was decoded as part of research on disease control and selective breeding in apple production.There are more than 7,500 known cultivars of apples, resulting in a range of desired characteristics. Different cultivars are bred for various tastes and uses, including cooking, fresh eating and cider production. Domestic apples are generally propagated by grafting, although wild apples grow readily from seed. Trees are prone to a number of fungal, bacterial and pest problems, which can be controlled by a number of organic and non-organic means.\n" +
//                "\n" +
//                "About 69 million tons of apples were grown worldwide in 2010, and China produced almost half of this total. The United States is the second-leading producer, with more than 6% of world production. Turkey is third, followed by Italy, India and Poland. Apples are often eaten raw, but can also be found in many prepared foods (especially desserts) and drinks. Many beneficial health effects are thought to result from eating apples; however, two forms of allergies are seen to various proteins found in the fruit.\n" +
//                "Pests and diseases\n" +
//                "Leaves with significant insect damage\n" +
//                "Main article: List of apple diseases\n" +
//                "See also: List of Lepidoptera that feed on Malus\n" +
//                "\n" +
//                "Apple trees are susceptible to a number of fungal and bacterial diseases and insect pests. Many commercial orchards pursue an aggressive program of chemical sprays to maintain high fruit quality, tree health, and high yields. A trend in orchard management is the use of organic methods. These ban the use of some pesticides, though some older pesticides are allowed. Organic methods include, for instance, introducing its natural predator to reduce the population of a particular pest.\n" +
//                "\n" +
//                "A wide range of pests and diseases can affect the plant; three of the more common diseases/pests are mildew, aphids and apple scab.\n" +
//                "\n" +
//                "    Mildew: which is characterized by light grey powdery patches appearing on the leaves, shoots and flowers, normally in spring. The flowers will turn a creamy yellow color and will not develop correctly. This can be treated in a manner not dissimilar from treating Botrytis; eliminating the conditions which caused the disease in the first place and burning the infected plants are among the recommended actions to take.[54]\n" +
//                "    Aphids: There are five species of aphids commonly found on apples: apple grain aphid, rosy apple aphid, apple aphid, spirea aphid and the woolly apple aphid. The aphid species can be identified by their color, the time of year when they are present and by differences in the cornicles, which are small paired projections from the rear of aphids.[54] Aphids feed on foliage using needle-like mouth parts to suck out plant juices. When present in high numbers, certain species reduce tree growth and vigor.[55]\n" +
//                "    Apple scab: Apple scab causes leaves to develop olive-brown spots with a velvety texture that later turn brown and become cork-like in texture. The disease also affects the fruit, which also develops similar brown spots with velvety or cork-like textures. Apple scab is spread through fungus growing in old apple leaves on the ground and spreads during warm spring weather to infect the new year&apos;s growth.[56]\n" +
//                "\n" +
//                "Among the most serious disease problems are fireblight, a bacterial disease; and Gymnosporangium rust, and black spot, two fungal diseases.[55] Codling moths and apple maggots are two other pests which affect apple trees. Young apple trees are also prone to mammal pests like mice and deer, which feed on the soft bark of the trees, especially in winter.[56]"

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
            val response = service.getLecture(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.d("INFO:", prettyJson)

                    val info = Gson().fromJson<LectionModel>(prettyJson)
                    Log.d("INFO2::", "${info.id_lesson}, ${info.id_module}, ${info.content}, ${info.discript}, ${info.name}, ${info.video}")
                    tvNameLection.text = info.name
                    tvContent.text = info.content

                    val ytPlayer = findViewById<YouTubePlayerView>(R.id.youTube)
                    ytPlayer.initialize(apiKey, object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            provider: YouTubePlayer.Provider?,
                            player: YouTubePlayer?,
                            p2: Boolean
                        ) {
                            // loadVideo - это код видео, надо сделать парсер ссылки ютуба, чтоб выуживал код видео
                            player?.loadVideo(getYoutubeVideoFromUrl(info.video))
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


                } else if (response.code() == 401){
                    val refresh_response = service.refreshToken()



                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }




    }



}