package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.Property
import com.spit91.maskani.domain.model.repository.PropertyRepository
import com.spit91.maskani.domain.model.repository.SavedPropertyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetSavedPropertiesUseCase @Inject constructor(
    private val savedPropertyRepository:  SavedPropertyRepository,
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(): Result<List<Property>> {
        val ids = savedPropertyRepository.getSavedPropertyIds().getOrElse { return Result.failure(it)}

        val results = coroutineScope {
            ids.map{ id -> async {propertyRepository.getPropertyById(id)}} .awaitAll()
        }
        val properties = results.mapNotNull { it.getOrNull()}
        return Result.success(properties)
    }
}