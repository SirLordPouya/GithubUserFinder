package com.pouyaheydari.github.userfinder.features.details.domain

import com.pouyaheydari.github.userfinder.base.usecase.UseCase
import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetails
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<String, UserDetails>() {

    override suspend fun run(param: String): UserDetails = userRepository.getUser(param)
}
