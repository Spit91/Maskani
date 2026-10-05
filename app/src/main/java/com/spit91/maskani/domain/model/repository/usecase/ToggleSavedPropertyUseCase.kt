package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.repository.SavedPropertyRepository
import javax.inject.Inject

class ToggleSavedPropertyUseCase @Inject constructor(
    private val savedPropertyRepository: SavedPropertyRepository
) {
    suspend operator fun invoke (propertyId: String, currentlySaved: Boolean): Result<Unit> {
        if (propertyId.isBlank()) {
            return Result.failure(IllegalArgumentException("Property could not be identified"))
        }
        return if (currentlySaved){
            savedPropertyRepository.unsaveProperty(propertyId)
        } else {
            savedPropertyRepository.saveProperty(propertyId)
        }
    }
}