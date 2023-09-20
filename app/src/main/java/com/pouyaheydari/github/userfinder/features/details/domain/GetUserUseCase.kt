package com.pouyaheydari.github.userfinder.features.details.domain

import com.pouyaheydari.github.userfinder.base.usecase.UseCase
import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    UseCase<String, UserDetailsDataState>() {

    override suspend fun run(param: String) = userRepository.getUser(param)
}
