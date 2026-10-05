package com.spit91.maskani.domain.model.repository

import com.spit91.maskani.domain.model.Property

interface PropertyRepository {
    suspend fun getPublishedProperties(): Result<List<Property>>
    suspend fun getPropertyById(id: String): Result<Property?>
}