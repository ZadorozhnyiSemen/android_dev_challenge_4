package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.domain.entity.location.Location
import javax.inject.Inject

class LocationMapper @Inject constructor(): Mapper<DataLocation, Location> {

    override fun mapToDomain(dataValue: DataLocation): Location {
        return Location(dataValue.name, dataValue.country, dataValue.lat, dataValue.lng)
    }

    override fun mapToData(domainValue: Location): DataLocation {
        return DataLocation(domainValue.name, domainValue.country, domainValue.latitude, domainValue.longitude)
    }

    override fun mapListToDomain(dataValue: List<DataLocation>): List<Location> {
        return dataValue.map { mapToDomain(it) }
    }

    override fun mapListToData(domainValue: List<Location>): List<DataLocation> {
        return domainValue.map { mapToData(it) }
    }
}