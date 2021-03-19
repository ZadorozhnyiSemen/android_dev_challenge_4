package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.domain.entity.location.Location

class LocationMapper: Mapper<List<DataLocation>, List<Location>> {

    override fun mapToDomain(dataValue: List<DataLocation>): List<Location> {
        return dataValue.map { Location(it.name, it.lat, it.lng) }
    }

    override fun mapToData(domainValue: List<Location>): List<DataLocation> {
        return domainValue.map { DataLocation(it.name, it.latitude, it.longitude) }
    }
}