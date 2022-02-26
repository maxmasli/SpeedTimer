package masli.prof.domain.models

data class ResultModel(
    var id: Int = 0,
    val scramble: String,
    val time: Long,
    val description: String,
    val isDNF: Boolean,
    val isPlus: Boolean
)
