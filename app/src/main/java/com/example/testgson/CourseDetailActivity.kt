package com.example.testgson

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testgson.CustomViewHolder.Companion.VIDEO_TITLE_KEY
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
//        recyclerView_main.adapter = CourseDetailAdapter()

        // we'll change the nav bar title..
        val navBarTitle = intent.getStringExtra(VIDEO_TITLE_KEY)
//        val navBarTitle = intent.getStringExtra("log1")
        supportActionBar?.title = navBarTitle


//        println(courseDetailUrl)

        fetchJSON()
    }

    private fun fetchJSON() {
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
//        val videoId = "3"
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId
        println(videoId)


        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = CourseDetailAdapter(courseLessons)
                }

//                println("body")
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")

            }
        })
    }


    private class CourseDetailAdapter(val courseLessons: Array<CourseLesson>) :
        RecyclerView.Adapter<CourseLessonViewHolder>() {

        override fun getItemCount(): Int {
            return courseLessons.size
        }

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(p0.context)
            val customView = layoutInflater.inflate(R.layout.course_lesson_row, p0, false)

            return CourseLessonViewHolder(customView)
        }

        override fun onBindViewHolder(p0: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons[position]

            p0.customView.textView_course_lesson_title?.text = courseLesson.name
            p0.customView.textView_duration?.text = courseLesson.duration

            val imageView = p0.customView.imageView_course_lesson_thumbnail
            Picasso.get().load(courseLesson.imageUrl).into(imageView)

            p0.courseLesson = courseLesson

        }

    }

    class CourseLessonViewHolder(val customView: View, var courseLesson: CourseLesson? = null) :
        RecyclerView.ViewHolder(customView) {

        companion object {
            val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }

        init {
            customView.setOnClickListener {
                println("Attempt to load webview somehow???")

                val intent = Intent(customView.context, CourseLessonActivity::class.java)

                intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)
                Log.d("AMO3", "${courseLesson?.link}")

                customView.context.startActivity(intent)
            }
        }

    }

}
