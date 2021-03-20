package com.example.androiddevchallenge.data.mappers

interface Mapper<Data, Domain> {

    fun mapToDomain(dataValue: Data): Domain

    fun mapToData(domainValue: Domain): Data

    fun mapListToDomain(dataValue: List<Data>): List<Domain>

    fun mapListToData(domainValue: List<Domain>): List<Data>
}