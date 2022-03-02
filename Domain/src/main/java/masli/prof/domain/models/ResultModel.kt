package masli.prof.domain.models

import masli.prof.domain.enums.EventEnum
import java.io.Serializable

data class ResultModel(
    var id: Int = 0,
    val event: EventEnum,
    val scramble: String,
    val time: Long,
    var description: String,
    var isDNF: Boolean,
    var isPlus: Boolean
) : Serializable
