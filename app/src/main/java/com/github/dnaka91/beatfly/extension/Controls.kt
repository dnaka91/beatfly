/*
 * Copyright (C) 2019 Dominik Nakamura
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
