package masli.prof.domain.usecases

import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultAvgModel
import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class GetAvgByEventUseCase(private val resultsRepository: ResultsRepository) {
    fun execute(event: EventEnum): ResultAvgModel {
        val resultListByEvent = resultsRepository.getAllResult().filter { result -> result.event == event } // filter by event
        return ResultAvgModel(
            avg5 = getAvgByEvent( 5, resultListByEvent),
            avg12 = getAvgByEvent( 12, resultListByEvent),
            avg50 = getAvgByEvent( 50, resultListByEvent),
            avg100 = getAvgByEvent(100, resultListByEvent),
            best = getBestByEvent(resultListByEvent),
            count = getCountByEvent(resultListByEvent)
        )
    }

    private fun getCountByEvent(resultListByEvent: List<ResultModel>): Int{
        return resultListByEvent.size
    }

    private fun getBestByEvent(resultListByEvent: List<ResultModel>): Long? {
        var min = Long.MAX_VALUE
        for (resultModel in resultListByEvent) {
            if (resultModel.time < min) min = resultModel.time
        }
        if (min == Long.MAX_VALUE) return null
        return min
    }

    private fun getAvgByEvent(avg: Int, resultListByEvent: List<ResultModel>): Long? {

        if (resultListByEvent.size < avg) return null
        val resultListWithEnabledResult =  resultListByEvent.asReversed().subList(0, avg) // only usable results
        val resultTimeList = mutableListOf<Long?>()
        resultListWithEnabledResult.forEach { result ->
            when {
                result.isDNF -> resultTimeList.add(null)
                result.isPlus -> resultTimeList.add(result.time + 2000)
                else -> resultTimeList.add(result.time)
            }
        }

        if (resultTimeList.count { it == null } >= 2) { // if count of null >= 2
            return null
        }
        var bestElem: Long = Long.MAX_VALUE
        var worstElem: Long? = 0
        resultTimeList.forEach { time ->
            if(time == null) worstElem = null
            if (time != null) {
                if (time < bestElem) bestElem = time
            }
            if (time != null) {
                if (worstElem != null && time > worstElem!!) worstElem = time
            }
        }

        resultTimeList.remove(bestElem)
        resultTimeList.remove(worstElem)
        var sum: Long = 0
        resultTimeList.forEach {time -> sum += time!! }
        return sum / (avg - 2)
    }
}