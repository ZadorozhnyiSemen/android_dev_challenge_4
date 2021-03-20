package com.example.androiddevchallenge.data.source.user

import android.content.Context
import android.provider.ContactsContract
import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class PreferencesDb(
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