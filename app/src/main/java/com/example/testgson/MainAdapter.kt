package com.example.testgson

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    val videoTitles = listOf("First title", "Second", "3rd", "MOOOOORE TITLE")

    // numberOfItems
    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, p0, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, position: Int) {
//        val videoTitle = videoTitles.get(position)
        val video = homeFeed.videos.get(position)
        p0.view.textView_video_title?.text = video.name

        p0.view.textView_channel_name?.text = video.channel.name + "  •  " + "20K Views\n4 days ago"

//load pic's over picasso to thumbnailImageView
        val thumbnailImageView = p0.view.imageView_video_thumbnail
        Picasso.get().load(video.imageUrl).into(thumbnailImageView)
//load pic's over picasso to channelProfileImageView
        val channelProfileImageView = p0.view.imageView_channel_profile
        Picasso.get().load(video.channel.profileImageUrl).into(channelProfileImageView)
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
//start other activity by click)))
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, CourseDetailActivity::class.java)

            view.context.startActivity(intent)
        }
    }


}
