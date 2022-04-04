package masli.prof.domain.usecases

import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultAvgModel
import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAvgByEventUseCaseTest {

    @Test
    fun `should return correct avg`() {
        val testList = listOf(
            ResultModel(event = EventEnum.Event3by3, scramble = "", description = "", time = 456L, isDNF = false, isPlus = true),
            ResultModel(event = EventEnum.Event3by3, scramble = "", description = "", time = 567L, isDNF = true, isPlus = false),
            ResultModel(event = EventEnum.Event2by2, scramble = "", description = "", time = 516L, isDNF = false, isPlus = false),
            ResultModel(event = EventEnum.Event3by3, scramble = "", description = "", time = 242L, isDNF = false, isPlus = false),
            ResultModel(event = EventEnum.Event3by3, scramble = "", description = "", time = 912L, isDNF = false, isPlus = false),
            ResultModel(event = EventEnum.Event3by3, scramble = "", description = "", time = 245L, isDNF = false, isPlus = false),
        )

        val resultsRepository = mock<ResultsRepository>()
        val useCase = GetAvgByEventUseCase(resultsRepository = resultsRepository)

        Mockito.`when`(resultsRepository.getAllResult()).thenReturn(testList)
        val actual = useCase.execute(EventEnum.Event3by3)

        val excepted = ResultAvgModel(
            avg5 = 1204L,
            avg12 = null,
            avg50 = null,
            avg100 = null
        )

        Assertions.assertEquals(excepted, actual)
    }
}