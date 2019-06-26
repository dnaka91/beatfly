package com.github.dnaka91.beatfly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.Song
import com.github.dnaka91.beatfly.thirdparty.glide.GlideApp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.history_item.*
import org.jetbrains.anko.support.v4.dip
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime
import javax.inject.Inject

class HistoryListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<Song>()

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            inflater.inflate(
                R.layout.history_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = data[position]
        holder.apply {
            title.text = song.title
            album.text = song.album
            artist.text = song.artist

            val time = LocalTime.ofNanoOfDay(Duration.ofMillis(song.duration).toNanos())
            duration.text = fragment.getString(
                R.string.song_duration,
                time.minute,
                time.second,
                Duration.ofNanos(time.nano.toLong()).toMillis()
            )

            GlideApp.with(fragment)
                .load(song.cover.url)
                .placeholder(R.drawable.placeholder_history)
                .transform(RoundedCorners(fragment.dip(4)))
                .transition(withCrossFade())
                .into(picture)
        }
    }

    fun setData(list: List<Song>) {
        data = list
        notifyDataSetChanged()
    }
}