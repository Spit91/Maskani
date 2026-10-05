package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.Property
import com.spit91.maskani.domain.model.repository.PropertyRepository
import javax.inject.Inject

class GetPublishedPropertiesUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository
){
    suspend operator fun invoke(): Result<List<Property>> {
        return propertyRepository.getPublishedProperties()
    }
}