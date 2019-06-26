package com.github.dnaka91.beatfly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.service.RadioService
import com.github.dnaka91.beatfly.thirdparty.glide.GlideApp
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.song_detail_fragment.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

class SongDetailFragment : DaggerFragment() {

    @Inject
    internal lateinit var radioService: RadioService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.song_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        radioService.currentSong().observe(this, Observer {
            if (it == null) return@Observer

            title.text = it.title
            album.text = it.album
            artist.text = it.artist

            GlideApp.with(this)
                .load(it.cover.url)
                .placeholder(R.drawable.placeholder_album)
                .transform(RoundedCorners(dip(8)))
                .transition(withCrossFade())
                .into(picture)
        })
    }
}
