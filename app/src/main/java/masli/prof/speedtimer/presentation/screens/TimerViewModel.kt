package masli.prof.speedtimer.presentation.screens

import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultModel
import masli.prof.domain.usecases.*
import masli.prof.speedtimer.utils.mapToTime

class TimerViewModel(
    private val getScrambleUseCase: GetScrambleUseCase,
    private val saveResultUseCase: SaveResultUseCase,
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val deleteResultUseCase: DeleteResultUseCase,
    private val updateResultUseCase: UpdateResultUseCase
) : ViewModel() {
    //LiveData
    private val scrambleMutableLivedata = MutableLiveData<String>()// to show scramble
    val scrambleLiveData = scrambleMutableLivedata as LiveData<String>

    private val timeMutableLiveData = MutableLiveData<String>()// to show time
    val timeLiveData = timeMutableLiveData as LiveData<String>

    private val timerIsStartMutableLiveData = MutableLiveData<Boolean>()// to change timer to "..."
    val timerIsStartLiveData = timerIsStartMutableLiveData as LiveData<Boolean>

    private val isReadyMutableLiveData = MutableLiveData<Boolean>() // to make timer green
    val isReadyLiveData = isReadyMutableLiveData as LiveData<Boolean>

    private val currentEventMutableLiveData = MutableLiveData<EventEnum>()// to set icon
    val currentEventLiveData = currentEventMutableLiveData as LiveData<EventEnum>

    private val isDNFMutableLiveData = MutableLiveData<Boolean>()// to set dnf
    val isDNFLiveData = isDNFMutableLiveData as LiveData<Boolean>

    private val isPlusMutableLiveData = MutableLiveData<Boolean>()// to set plus 2
    val isPlusLiveData = isPlusMutableLiveData as LiveData<Boolean>

    //private variables
    private var timeMillisStart: Long = 0
    private var currentResult: ResultModel? = null
    private val defaultTime = "0.000"

    init {
        timerIsStartMutableLiveData.value = false
        isReadyMutableLiveData.value = false
        currentEventMutableLiveData.value = EventEnum.Event3by3
        isDNFMutableLiveData.value = false
        isPlusMutableLiveData.value = false

        // generate new scramble
        getScramble()
    }

    fun timerActionDown() {
        if (timerIsStartMutableLiveData.value == true) { // end solve
            val timeMillis = System.currentTimeMillis() - timeMillisStart
            val scramble = scrambleMutableLivedata.value.toString()
            val event = currentEventMutableLiveData.value!!

            timeMillisStart = 0
            timeMutableLiveData.value = mapToTime(timeMillis)

            currentResult = ResultModel(
                event = event,
                scramble = scramble,
                time = timeMillis,
                description = "",
                isDNF = false,
                isPlus = false
            )

            saveResult(currentResult!!)
            getScramble() // for next solve

        } else {// solver pressed down and ready to start
            isReadyMutableLiveData.value = true //make timer green
        }
    }

    fun timerActionUp() {
        if (timerIsStartMutableLiveData.value == false) {// start solve
            timeMillisStart = System.currentTimeMillis()
            timerIsStartMutableLiveData.value = true
            isReadyMutableLiveData.value = false // make timer black
            isDNFMutableLiveData.value = false // make buttons gray
            isPlusMutableLiveData.value = false

        } else {
            timerIsStartMutableLiveData.value = false
        }
    }

    fun setDNFResult() { // pressed dnf
        if (currentResult != null) {
            val currentIsDnf = currentResult!!.isDNF
            currentResult!!.isDNF = currentIsDnf.not()
            currentResult!!.isPlus = false
            if (currentResult!!.isDNF) timeMutableLiveData.value = "DNF" // set DNF
            else timeMutableLiveData.value = mapToTime(currentResult!!.time)

            update()
        }
    }

    fun setPlusResult() { // pressed +2
        if (currentResult != null) {
            val currentIsPlus = currentResult!!.isPlus
            currentResult!!.isPlus = currentIsPlus.not()
            currentResult!!.isDNF = false
            if (currentResult!!.isPlus) timeMutableLiveData.value = mapToTime(currentResult!!.time + 2000) // plus 2
            else timeMutableLiveData.value = mapToTime(currentResult!!.time)

            update()
        }
    }

    fun setEvent(event: EventEnum) {
        currentEventMutableLiveData.value = event
        getScramble()// update scramble
    }

    private fun update() {
        isDNFMutableLiveData.value = currentResult!!.isDNF
        isPlusMutableLiveData.value = currentResult!!.isPlus
        updateResult(currentResult!!)
    }

    private fun updateResult(result: ResultModel) {
        GlobalScope.launch(Dispatchers.Default) {
            val lastResult = getAllResultsUseCase.execute().last()
            result.id = lastResult.id
            updateResultUseCase.execute(result)
        }
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

    fun deleteResult() {
        GlobalScope.launch(Dispatchers.Default) {
            // also get id
            if(currentResult != null) {
                val lastResult = getAllResultsUseCase.execute().last()
                currentResult!!.id = lastResult.id
                deleteResultUseCase.execute(currentResult!!)
                currentResult = null

                isDNFMutableLiveData.postValue(false)
                isPlusMutableLiveData.postValue(false)
                timeMutableLiveData.postValue(defaultTime)
            }
        }
    }
}