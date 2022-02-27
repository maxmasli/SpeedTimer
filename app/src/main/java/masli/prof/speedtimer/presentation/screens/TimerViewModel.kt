package masli.prof.speedtimer.presentation.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import masli.prof.domain.enums.EventEnum
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
    //LiveData
    private val scrambleMutableLivedata = MutableLiveData<String>()// to show scramble
    val scrambleLiveData = scrambleMutableLivedata as LiveData<String>

    private val timeMutableLiveData = MutableLiveData<Long>()// to show time
    val timeLiveData = timeMutableLiveData as LiveData<Long>

    private val timerIsStartMutableLiveData = MutableLiveData<Boolean>()// to change timer to "..."
    val timerIsStartLiveData = timerIsStartMutableLiveData as LiveData<Boolean>

    private val isReadyMutableLiveData = MutableLiveData<Boolean>() // to make timer green
    val isReadyLiveData = isReadyMutableLiveData as LiveData<Boolean>

    private val currentEventMutableLiveData = MutableLiveData<EventEnum>()// to set icon
    val currentEventLiveData = currentEventMutableLiveData as LiveData<EventEnum>

    //private variables
    private var timeMillisStart: Long = 0

    init {
        timerIsStartMutableLiveData.value = false
        isReadyMutableLiveData.value = false
        currentEventMutableLiveData.value = EventEnum.Event3by3

        // generate new scramble
        getScramble()
    }

    fun timerActionDown() {
        if (timerIsStartMutableLiveData.value == true) { // end solve
            val timeMillis = System.currentTimeMillis() - timeMillisStart
            val scramble = scrambleMutableLivedata.value.toString()

            timeMillisStart = 0
            timeMutableLiveData.value = timeMillis

            saveResult(ResultModel(
                event = currentEventMutableLiveData.value!!,
                scramble = scramble,
                time = timeMillis,
                description = "",
                isDNF = false,
                isPlus = false
            ))

            getScramble() // for next solve

        } else {// solver pressed down and ready to start
            isReadyMutableLiveData.value = true //make timer green
        }
    }

    fun timerActionUp() {
        if (timerIsStartMutableLiveData.value == false) {
            timerIsStartMutableLiveData.value = true // start solve
            isReadyMutableLiveData.value = false // make timer black
            timeMillisStart = System.currentTimeMillis()
        } else {
            timerIsStartMutableLiveData.value = false
        }
    }

    fun setEvent(event: EventEnum) {
        currentEventMutableLiveData.value = event
        getScramble()// update scramble
    }

    private fun getScramble() {
        val scramble = getScrambleUseCase.execute(currentEventMutableLiveData.value!!)
        scrambleMutableLivedata.value = scramble
    }

    private fun saveResult(result: ResultModel) {
        GlobalScope.launch(Dispatchers.Default) {
            saveResultUseCase.execute(result)
        }
    }

//    fun getAllResults() {
//        GlobalScope.launch(Dispatchers.Default) {
//            val listResult = getAllResultsUseCase.execute()
//            allResultsMutableLiveData.postValue(listResult)
//        }
//    }

    fun deleteResult(result: ResultModel) {
        GlobalScope.launch(Dispatchers.Default) {
            deleteResultUseCase.execute(result)
        }
    }
}