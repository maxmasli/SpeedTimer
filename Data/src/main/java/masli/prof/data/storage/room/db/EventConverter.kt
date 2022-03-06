package masli.prof.data.storage.room.db

import androidx.room.TypeConverter
import masli.prof.domain.enums.EventEnum

class EventConverter {

    @TypeConverter
    fun fromEvent(value: EventEnum): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toEvent(value: Int): EventEnum {
        return when (value) {
            0 -> EventEnum.Event3by3
            1 -> EventEnum.Event2by2
            2 -> EventEnum.EventPyra
            else -> EventEnum.Event3by3
        }
    }
}