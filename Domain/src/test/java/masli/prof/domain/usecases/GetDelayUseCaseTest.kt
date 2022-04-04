package masli.prof.domain.usecases

import masli.prof.domain.repositories.SharedPrefsRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

//class TestRepository: SharedPrefsRepository {
//    override fun getTheme(): ThemeEnum {
//        TODO()
//    }
//
//    override fun setTheme(themeEnum: ThemeEnum) {
//        TODO()
//    }
//
//    override fun getDelay(): Long {
//        return 300L
//    }
//
//    override fun setDelay(delay: Long) {
//        TODO()
//    }
//}

class GetDelayUseCaseTest {

    val sharedPrefsRepository = mock<SharedPrefsRepository>()


    @Test
    fun `should return the same data as in repository`(){

        val testDelay = 300L
        Mockito.`when`(sharedPrefsRepository.getDelay()).thenReturn(testDelay)

        val useCase = GetDelayUseCase(sharedPrefsRepository = sharedPrefsRepository)
        val result = useCase.execute()
        val expected = 300L

        Assertions.assertEquals(expected, result)

    }
}