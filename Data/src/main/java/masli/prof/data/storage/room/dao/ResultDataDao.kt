package masli.prof.data.storage.room.dao

import androidx.room.*
import masli.prof.data.RESULT_DATABASE_NAME
import masli.prof.data.storage.models.ResultDataModel

@Dao
interface ResultDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(resultData: ResultDataModel)

    @Delete
    suspend fun delete(resultData: ResultDataModel)

    @Query("SELECT * from $RESULT_DATABASE_NAME")
    fun getAllNotes(): List<ResultDataModel>
}