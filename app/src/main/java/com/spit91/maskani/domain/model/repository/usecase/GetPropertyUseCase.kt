package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.Property
import com.spit91.maskani.domain.model.repository.PropertyRepository
import javax.inject.Inject

class GetPropertyUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(propertyId: String): Result<Property?>{
        if (propertyId.isBlank()) {
            return Result.failure(IllegalArgumentException("Property could not be identified"))
        }
        return propertyRepository.getPropertyById(propertyId)
    }

}