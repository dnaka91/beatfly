package com.github.dnaka91.beatfly.extension

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

inline fun <reified T> Moshi.adapter(): JsonAdapter<T> = adapter(T::class.java)

inline fun <reified C : List<T>, reified T> Moshi.listAdapter(): JsonAdapter<C> = adapter(parameterizedType<C, T>())

inline fun <reified C, reified T> parameterizedType(): ParameterizedType =
    Types.newParameterizedType(C::class.java, T::class.java)
