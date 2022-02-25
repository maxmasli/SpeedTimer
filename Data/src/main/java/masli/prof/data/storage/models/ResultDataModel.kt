package masli.prof.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import masli.prof.data.RESULT_DATABASE_NAME

@Entity(tableName = RESULT_DATABASE_NAME)
data class ResultDataModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
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
