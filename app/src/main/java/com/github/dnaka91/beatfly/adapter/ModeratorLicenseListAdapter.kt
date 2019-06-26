package com.github.dnaka91.beatfly.adapter

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.ModeratorLicense
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.moderator_license_item.*
import javax.inject.Inject

class ModeratorLicenseListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<ModeratorLicenseListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<ModeratorLicense>()

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            inflater.inflate(
                R.layout.moderator_license_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val license = data[position]
        holder.apply {
            name.text = license.name
            picture.text = Html.fromHtml(
                fragment.getString(
                    R.string.mod_license_picture,
                    license.picture.artist.url,
                    license.picture.artist.value,
                    license.picture.platform.url,
                    license.picture.platform.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )

            picture.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    fun setData(list: List<ModeratorLicense>) {
        data = list
        notifyDataSetChanged()
    }
}