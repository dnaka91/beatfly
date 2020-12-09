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
package com.github.dnaka91.beatfly.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.findNavController
import com.github.dnaka91.beatfly.NavGraphDirections
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

typealias Action = (View) -> Unit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_more_vert_24)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about -> {
            findNavController(R.id.nav_host_fragment).navigate(NavGraphDirections.actionAbout())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun showPlayer(action: Action? = null) {
        binding.fab.run {
            doOnPreDraw {
                binding.fab.show()
                binding.toolbar.performShow()
            }
            setOnClickListener(action)
        }
    }

    fun hidePlayer() {
        binding.fab.doOnPreDraw {
            binding.fab.hide()
            binding.toolbar.performHide()
        }
    }

    fun setFabIcon(@DrawableRes resId: Int) {
        binding.fab.setImageResource(resId)
    }

    fun showSnackbar(text: CharSequence, length: Int) {
        Snackbar.make(binding.fab, text, length)
            .setAnchorView(binding.fab)
            .show()
    }
}
