package masli.prof.speedtimer.presentation.screens.timerscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import masli.prof.domain.enums.EventEnum
import masli.prof.domain.enums.ThemeEnum
import masli.prof.domain.models.ResultAvgModel
import masli.prof.domain.models.ResultModel
import masli.prof.speedtimer.themes.Theme
import masli.prof.domain.usecases.*
import masli.prof.speedtimer.themes.DefaultTheme
import masli.prof.speedtimer.themes.GreenNeonTheme
import masli.prof.speedtimer.themes.GreenYellowNeonTheme
import masli.prof.speedtimer.themes.HoneyTheme
import masli.prof.speedtimer.utils.mapToTime

class TimerViewModel(
    private val getScrambleUseCase: GetScrambleUseCase,
    private val saveResultUseCase: SaveResultUseCase,
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val deleteResultUseCase: DeleteResultUseCase,
    private val updateResultUseCase: UpdateResultUseCase,
    private val getAvgByEventUseCase: GetAvgByEventUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val getDelayUseCase: GetDelayUseCase
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

    private val avgResultMutableLiveData = MutableLiveData<ResultAvgModel>() // to count avg
    val avgResultLiveData = avgResultMutableLiveData as LiveData<ResultAvgModel>



    //private variables
    private var timeMillisStart: Long = 0
    private var timeDelay: Long = getDelayUseCase.execute()
    private var isPressed = false
    private val defaultTime = "0.000"
    private var delayCoroutineScope: CoroutineScope? = null

    var currentResult: ResultModel? = null //maybe change to MutableLiveData

    init {
        timerIsStartMutableLiveData.value = false
        isReadyMutableLiveData.value = false
        currentEventMutableLiveData.value = EventEnum.Event3by3
        isDNFMutableLiveData.value = false
        isPlusMutableLiveData.value = false

        getScramble()
        getAvg()
    }

    fun timerActionDown() {
        timeDelay = getDelayUseCase.execute()
        isPressed = true
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
            viewModelScope.launch (Dispatchers.Default){
                delayCoroutineScope = this
                delay(timeDelay)
                if (isPressed) isReadyMutableLiveData.postValue(true) //make timer green
                else isReadyMutableLiveData.postValue(false) // make timer black
            }

        }
    }

    fun timerActionUp() {
        isPressed = false
        delayCoroutineScope?.cancel()
        if (timerIsStartMutableLiveData.value == false && isReadyMutableLiveData.value == true) {// start solve
            timeMillisStart = System.currentTimeMillis()
            timerIsStartMutableLiveData.value = true
            isReadyMutableLiveData.value = false // make timer black
            isDNFMutableLiveData.value = false // make buttons gray
            isPlusMutableLiveData.value = false

        } else {
            timerIsStartMutableLiveData.value = false
            isReadyMutableLiveData.value = false // make timer black
        }
    }

    fun setDNFResult() { // pressed dnf
        if (currentResult != null) {
            val currentIsDnf = currentResult!!.isDNF
            currentResult!!.isDNF = currentIsDnf.not()
            currentResult!!.isPlus = false
            if (currentResult!!.isDNF) timeMutableLiveData.value = "DNF" // set DNF
            else timeMutableLiveData.value = mapToTime(currentResult!!.time)

            updateResultPenalties()
        }
    }

    fun setPlusResult() { // pressed +2
        if (currentResult != null) {
            val currentIsPlus = currentResult!!.isPlus
            currentResult!!.isPlus = currentIsPlus.not()
            currentResult!!.isDNF = false
            if (currentResult!!.isPlus) timeMutableLiveData.value = mapToTime(currentResult!!.time + 2000) // plus 2
            else timeMutableLiveData.value = mapToTime(currentResult!!.time)

            updateResultPenalties()
        }
    }

    fun setEvent(event: EventEnum) {
        currentEventMutableLiveData.value = event
        currentResult = null
        clearTimer()
        getScramble()// update scramble
        getAvg()
    }

    private fun updateResultPenalties() {
        isDNFMutableLiveData.value = currentResult!!.isDNF
        isPlusMutableLiveData.value = currentResult!!.isPlus
        updateResult(currentResult!!)
    }

    fun updateResult(result: ResultModel) {
        viewModelScope.launch(Dispatchers.Default) {
            val lastResult = getAllResultsUseCase.execute().last()
            result.id = lastResult.id
            updateResultUseCase.execute(result)
            getAvg()
        }
    }

    private fun saveResult(result: ResultModel) {
        viewModelScope.launch(Dispatchers.Default) {
            saveResultUseCase.execute(result)
            getAvg()
        }
    }

    fun deleteResultNoID() {
        viewModelScope.launch(Dispatchers.Default) {
            if(currentResult != null) {
                val lastResult = getAllResultsUseCase.execute().last()
                currentResult!!.id = lastResult.id
                deleteResultUseCase.execute(currentResult!!)
                currentResult = null

                clearTimer()
                getAvg()
            }
        }
    }

    fun getScramble() {
        val scramble = getScrambleUseCase.execute(currentEventMutableLiveData.value!!)
        scrambleMutableLivedata.value = scramble
    }

    fun setScramble(scramble: String) {
        scrambleMutableLivedata.value = scramble
    }

    fun getAvg() {
        viewModelScope.launch(Dispatchers.Default) {
            val avgResults = getAvgByEventUseCase.execute(currentEventMutableLiveData.value!!)
            avgResultMutableLiveData.postValue(avgResults)
        }
    }

    private fun clearTimer() { // restores the timer to its initial position
        isDNFMutableLiveData.postValue(false)
        isPlusMutableLiveData.postValue(false)
        timeMutableLiveData.postValue(defaultTime)
        currentResult = null
    }

    fun stopTimer() {
        clearTimer()
        timerIsStartMutableLiveData.value = false
    }

    fun getTheme(): Theme {
        return when(getThemeUseCase.execute()) {
            ThemeEnum.DefaultTheme -> DefaultTheme()
            ThemeEnum.GreenNeonTheme -> GreenNeonTheme()
            ThemeEnum.GreenYellowNeonTheme -> GreenYellowNeonTheme()
            ThemeEnum.HoneyTheme -> HoneyTheme()
        }
    }
}