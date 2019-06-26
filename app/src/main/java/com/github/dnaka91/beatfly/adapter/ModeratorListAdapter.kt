package com.github.dnaka91.beatfly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.Moderator
import com.github.dnaka91.beatfly.thirdparty.glide.GlideApp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.moderator_item.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

class ModeratorListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<ModeratorListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<Moderator>()

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            inflater.inflate(
                R.layout.moderator_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moderator = data[position]
        holder.apply {
            title.text = moderator.name
            rating.text = fragment.getString(
                R.string.mod_rating,
                moderator.rating,
                moderator.rateCount
            )

            GlideApp.with(fragment)
                .load(moderator.picture.url)
                .placeholder(R.drawable.placeholder_moderator)
                .transform(RoundedCorners(fragment.dip(4)))
                .transition(withCrossFade())
                .into(picture)
        }
    }

    fun setData(list: List<Moderator>) {
        data = list
        notifyDataSetChanged()
    }
}