package com.example.androiddevchallenge.data.source.user

import com.example.androiddevchallenge.data.entity.DataLocation
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

class PreferencesDb @Inject constructor(
    private val objectBox: BoxStore?
) {

    private val preferencesBox: Box<DataLocation>? = objectBox?.boxFor()

    fun getLocations(): List<DataLocation> = preferencesBox?.all ?: emptyList()

    fun storeLocation(location: DataLocation) {
        preferencesBox?.put(location)
    }

    fun removeLocation(location: DataLocation) {
        preferencesBox?.remove(location)
    }
}