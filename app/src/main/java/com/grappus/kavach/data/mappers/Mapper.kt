package com.grappus.kavach.data.mappers

interface Mapper<F, T> {
    fun fromMap(from: F): T
}