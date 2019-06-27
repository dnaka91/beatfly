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
@file:Suppress("NOTHING_TO_INLINE")

package com.github.dnaka91.beatfly.extension

import okio.BufferedSource
import okio.Okio
import okio.Source
import java.io.InputStream

/**
 * Convert the [InputStream] into a [Source].
 */
inline fun InputStream.toSource(): Source = Okio.source(this)

/**
 * Wrap the [Source] with a buffer, turning it into a [BufferedSource].
 */
inline fun Source.buffered(): BufferedSource = Okio.buffer(this)
