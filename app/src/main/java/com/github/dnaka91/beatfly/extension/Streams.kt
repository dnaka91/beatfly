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
