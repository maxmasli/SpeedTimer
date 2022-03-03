package masli.prof.domain.usecases

import masli.prof.domain.enums.EventEnum
import masli.prof.domain.models.ResultAvg
import masli.prof.domain.models.ResultModel
import masli.prof.domain.repositories.ResultsRepository

class GetAvgByEventUseCase(private val resultsRepository: ResultsRepository) {
    fun execute(event: EventEnum): ResultAvg {
        val resultList = resultsRepository.getAllResult()
        return ResultAvg(
            avg5 = getAvgByEvent(event, 5, resultList),
            avg12 = getAvgByEvent(event, 12, resultList),
            avg50 = getAvgByEvent(event, 50, resultList),
            avg100 = getAvgByEvent(event, 100, resultList)
        )
    }

    private fun getAvgByEvent(event: EventEnum, avg: Int, resultList: List<ResultModel>): Long? {
        val resultListByEvent =
            resultList.filter { result -> result.event == event } // filter by event
        if (resultListByEvent.size < avg) return null
        val resultListWithEnabledResult = resultListByEvent.asReversed()
            .subList(0, avg) // only usable results
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
            else if (time < bestElem) bestElem = time
            else if (worstElem != null && time > worstElem!!) worstElem = time
        }
        resultTimeList.remove(bestElem)
        resultTimeList.remove(worstElem)
        var sum: Long = 0
        resultTimeList.forEach {time -> sum += time!! }
        return sum / (avg - 2)
    }
}