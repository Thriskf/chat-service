package org.elteq.base.utils

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies


class MapperUtil : ModelMapper() {
    init {
        configuration.matchingStrategy = MatchingStrategies.STRICT
        configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
        configuration.isFieldMatchingEnabled = true
        configuration.isSkipNullEnabled = true
    }

    object Mapper {
        val mapper = MapperUtil()
        inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
    }
}