package masli.prof.speedtimer.presentation.listeners

import masli.prof.domain.models.ResultModel

interface DialogDetailsResultListener {
    fun updateResult(result: ResultModel)
}