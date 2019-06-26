package com.github.dnaka91.beatfly.extension

import android.app.Activity
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

typealias Action = (View) -> Unit

fun Activity.showPlayer(action: Action? = null) {
    fab?.run {
        doOnPreDraw {
            fab?.show()
            toolbar?.performShow()
        }
        setOnClickListener(action)
    }
}

fun Activity.hidePlayer() {
    fab?.doOnPreDraw {
        fab?.hide()
        toolbar?.performHide()
    }
}

fun Fragment.showPlayer(action: Action? = null) = activity?.showPlayer(action)

fun Fragment.hidePlayer() = activity?.hidePlayer()
