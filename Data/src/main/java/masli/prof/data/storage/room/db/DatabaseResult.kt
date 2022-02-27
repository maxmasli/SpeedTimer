package masli.prof.data.storage.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import masli.prof.data.RESULT_DATABASE_NAME
import masli.prof.data.storage.models.ResultDataModel
import masli.prof.data.storage.room.dao.ResultDataDao


@Database(entities = [ResultDataModel::class], version = 1)
@TypeConverters(EventConverter::class)
abstract class DatabaseResult: RoomDatabase() {
    abstract fun getResultDataDao(): ResultDataDao

    companion object {
        private var database: DatabaseResult? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseResult {
            return if (database == null) {
                database = Room.databaseBuilder(context, DatabaseResult::class.java, RESULT_DATABASE_NAME).build()
                database as DatabaseResult
            } else {
                database as DatabaseResult
            }
        }
    }
}