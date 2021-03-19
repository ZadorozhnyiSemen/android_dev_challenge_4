package com.example.androiddevchallenge.data.mappers

interface Mapper<Data, Domain> {

    fun mapToDomain(dataValue: Data): Domain

    fun mapToData(domainValue: Domain): Data
}