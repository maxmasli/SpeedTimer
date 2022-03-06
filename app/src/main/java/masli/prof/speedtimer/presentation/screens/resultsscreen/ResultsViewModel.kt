package masli.prof.speedtimer.presentation.screens.resultsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultAvg
import masli.prof.domain.models.ResultModel
import masli.prof.domain.usecases.DeleteResultUseCase
import masli.prof.domain.usecases.GetAllResultsUseCase
import masli.prof.domain.usecases.GetAvgByEventUseCase
import masli.prof.domain.usecases.UpdateResultUseCase

class ResultsViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val updateResultUseCase: UpdateResultUseCase,
    private val deleteResultUseCase: DeleteResultUseCase,
    private val getAvgByEventUseCase: GetAvgByEventUseCase,
) : ViewModel() {

    private val allResultsByEventMutableLiveData = MutableLiveData<List<ResultModel>>()
    val allResultsByEventLiveData = allResultsByEventMutableLiveData as LiveData<List<ResultModel>>

    private val currentEventMutableLiveData = MutableLiveData<EventEnum>()
    val currentEventLiveData = currentEventMutableLiveData as LiveData<EventEnum>

    private val avgResultMutableLiveData = MutableLiveData<ResultAvg>()
    val avgResultLiveData = avgResultMutableLiveData as LiveData<ResultAvg>

    init {
        getAvg()
    }

    fun deleteResult(result: ResultModel) {
        viewModelScope.launch(Dispatchers.Default) {
            deleteResultUseCase.execute(result)
            getAllResults()
        }
    }

    fun getAllResults() {
        viewModelScope.launch(Dispatchers.Default) {
            var listResults = getAllResultsUseCase.execute()
            listResults = listResults.filter { result ->
                result.event == currentEventMutableLiveData.value
            }
            allResultsByEventMutableLiveData.postValue(listResults.asReversed())
            getAvg() // in change all results, avg recount
        }
    }

    private fun getAvg() {
        viewModelScope.launch(Dispatchers.Default) {
            if (currentEventMutableLiveData.value != null) {
                val avgResults = getAvgByEventUseCase.execute(currentEventMutableLiveData.value!!)
                avgResultMutableLiveData.postValue(avgResults)
            }
        }
    }
    
    fun setEvent(event: EventEnum) {
        currentEventMutableLiveData.value = event
    }

    fun updateResult(result: ResultModel) {
        viewModelScope.launch(Dispatchers.Default) {
            updateResultUseCase.execute(result)
            getAllResults() // update to see description icon
        }
    }
}