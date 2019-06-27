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

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

inline fun <reified T> Moshi.adapter(): JsonAdapter<T> = adapter(T::class.java)

inline fun <reified C : List<T>, reified T> Moshi.listAdapter(): JsonAdapter<C> = adapter(parameterizedType<C, T>())

inline fun <reified C, reified T> parameterizedType(): ParameterizedType =
    Types.newParameterizedType(C::class.java, T::class.java)
