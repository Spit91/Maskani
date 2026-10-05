package com.spit91.maskani.presentation.auth.home

import com.spit91.maskani.domain.model.Property

data class HomeState(
    val isLoading: Boolean = true,
    val properties: List<Property> = emptyList(),
    val searchQuery: String = "",
    val selectedUnitType: String? = null, // null means all
    val selectedLocation: String? = null,
    val selectedPriceRange: PriceRange? = null,
    val savedIds: Set<String> = emptySet(),
    val transientMessage: String? = null,
    val errorMessage: String? = null
) {
    // The locations found in the loaded listings, for the location filter
    val availableLocations: List<String>
        get() = properties
            .map{it.location.trim()}
            .filter { it.isNotEmpty()}
            .distinct()
            .sorted()
    // The listings that match the current search text, unit type, location, and price range

    val visibleProperties: List<Property>
        get() {
            val query = searchQuery.trim()
            return properties.filter {property ->
                val matchesType = selectedUnitType == null ||
                        property.unitType == selectedUnitType
                val matchesLocation = selectedLocation == null ||
                        property.location.trim().equals(selectedLocation, ignoreCase = true)
                val matchesPriceRange = selectedPriceRange == null ||
                        (property.price >= selectedPriceRange.min &&
                                property.price < selectedPriceRange.max)

                val matchesQuery = query.isEmpty() ||
                        property.title.contains(query, ignoreCase = true) ||
                        property.location.contains(query, ignoreCase = true)
                matchesType && matchesLocation &&matchesPriceRange && matchesQuery
            }

            }

}