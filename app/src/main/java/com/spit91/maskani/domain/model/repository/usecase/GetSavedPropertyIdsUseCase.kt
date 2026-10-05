package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.repository.SavedPropertyRepository
import javax.inject.Inject

class GetSavedPropertyIdsUseCase @Inject constructor (
    private val savedPropertyRepository: SavedPropertyRepository
) {
    suspend operator fun invoke(): Result<List<String>> {
        return savedPropertyRepository.getSavedPropertyIds()
    }
}