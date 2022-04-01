package masli.prof.domain.models

import masli.prof.domain.enums.AlgorithmsEnum

data class AlgorithmsModel(
    var algName: String,
    var src: Int,
    var algorithmType: AlgorithmsEnum
)