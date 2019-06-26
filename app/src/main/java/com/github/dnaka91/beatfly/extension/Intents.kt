package com.github.dnaka91.beatfly.extension

import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.startService(action: String) =
    requireActivity().startService(Intent(requireContext(), T::class.java).also { it.action = action })