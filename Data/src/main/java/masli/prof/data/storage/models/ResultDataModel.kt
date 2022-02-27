package masli.prof.data.storage.models

import androidx.room.*
import masli.prof.data.RESULT_DATABASE_NAME
import masli.prof.data.storage.room.db.EventConverter
import masli.prof.domain.enums.EventEnum

@Entity(tableName = RESULT_DATABASE_NAME)
data class ResultDataModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    val event: EventEnum,
    @ColumnInfo
    val scramble: String,
    @ColumnInfo
    val time: Long,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val isDNF: Boolean,
    @ColumnInfo
    val isPlus: Boolean
)
