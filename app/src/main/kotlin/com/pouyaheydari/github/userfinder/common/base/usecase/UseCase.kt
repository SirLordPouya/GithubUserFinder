package com.pouyaheydari.github.userfinder.common.base.usecase

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<in PARAM, out RESULT>(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    protected abstract suspend fun run(param: PARAM): RESULT

    suspend operator fun invoke(param: PARAM): RESULT = withContext(coroutineContext) { run(param) }
}
