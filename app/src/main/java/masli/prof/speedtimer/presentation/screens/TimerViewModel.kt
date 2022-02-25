package masli.prof.speedtimer.presentation.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import masli.prof.domain.models.ResultModel
import masli.prof.domain.usecases.DeleteResultUseCase
import masli.prof.domain.usecases.GetAllResultsUseCase
import masli.prof.domain.usecases.GetScrambleUseCase
import masli.prof.domain.usecases.SaveResultUseCase

class TimerViewModel(
    private val getScrambleUseCase: GetScrambleUseCase,
    private val saveResultUseCase: SaveResultUseCase,
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val deleteResultUseCase: DeleteResultUseCase,
) : ViewModel() {

    private val allResultsMutableLiveData = MutableLiveData<List<ResultModel>>()
    val allResultsLiveData: LiveData<List<ResultModel>> = allResultsMutableLiveData

    fun getScramble() {
        getScrambleUseCase.execute()
    }

    fun saveResult(result: ResultModel) {
        GlobalScope.launch(Dispatchers.Default) {
            saveResultUseCase.execute(result)
            Log.e("AAA", "result saved")
        }
    }

    fun getAllResults() {
        GlobalScope.launch(Dispatchers.Default) {
            val listResult = getAllResultsUseCase.execute()
            allResultsMutableLiveData.postValue(listResult)
        }
    }

    fun deleteResult(result: ResultModel) {
        GlobalScope.launch(Dispatchers.Default) {
            deleteResultUseCase.execute(result)
        }
    }
}