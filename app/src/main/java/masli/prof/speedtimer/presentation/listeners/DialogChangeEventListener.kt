package masli.prof.speedtimer.presentation.listeners

import masli.prof.domain.enums.EventEnum

interface DialogChangeEventListener {
    fun setEvent(event: EventEnum)
}