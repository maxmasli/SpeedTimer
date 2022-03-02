package masli.prof.speedtimer.presentation.screens.resultsscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.domain.usecases.DeleteResultUseCase
import masli.prof.domain.usecases.GetAllResultsUseCase
import masli.prof.domain.usecases.UpdateResultUseCase

class ResultsViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val updateResultUseCase: UpdateResultUseCase,
    private val deleteResultUseCase: DeleteResultUseCase
) : ViewModel() {

    private val allResultsByEventMutableLiveData = MutableLiveData<List<ResultModel>>()
    val allResultsByEventLiveData = allResultsByEventMutableLiveData as LiveData<List<ResultModel>>

    private val currentEventMutableLiveData = MutableLiveData<EventEnum>()
    val currentEventLiveData = currentEventMutableLiveData as LiveData<EventEnum>

    fun getAllResults(){
        viewModelScope.launch(Dispatchers.Default) {
            var listResults = getAllResultsUseCase.execute()
            listResults = listResults.filter { result ->
                result.event == currentEventMutableLiveData.value
            }
            allResultsByEventMutableLiveData.postValue(listResults)
        }
    }

    fun setEvent(event: EventEnum) {
        currentEventMutableLiveData.value = event
    }

    fun updateResult(result: ResultModel) {
        viewModelScope.launch(Dispatchers.Default) {
            updateResultUseCase.execute(result)
        }
    }
}