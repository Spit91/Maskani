package com.spit91.maskani.domain.model.repository

interface SavedPropertyRepository {
    // IDs of the saved properties
    suspend fun getSavedPropertyIds(): Result<List<String>>
    suspend fun saveProperty(propertyId: String): Result<Unit>
    suspend fun unsaveProperty(propertyId: String): Result<Unit>
}