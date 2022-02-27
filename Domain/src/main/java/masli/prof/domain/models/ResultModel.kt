package masli.prof.domain.models

import masli.prof.domain.enums.EventEnum

data class ResultModel(
    var id: Int = 0,
    val event: EventEnum,
    val scramble: String,
    val time: Long,
    val description: String,
    val isDNF: Boolean,
    val isPlus: Boolean
)
