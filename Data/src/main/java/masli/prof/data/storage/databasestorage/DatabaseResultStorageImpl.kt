package masli.prof.data.storage.databasestorage

import masli.prof.data.storage.ResultStorage
import masli.prof.data.storage.models.ResultDataModel
import masli.prof.data.storage.room.dao.ResultDataDao

class DatabaseResultStorageImpl(private val resultDataDao: ResultDataDao) : ResultStorage {
    override val allNotes: List<ResultDataModel>
        get() = resultDataDao.getAllNotes()

    override suspend fun insertResultData(resultData: ResultDataModel) {
        resultDataDao.insert(resultData)
    }

    override suspend fun deleteResultData(resultData: ResultDataModel) {
        resultDataDao.delete(resultData)
    }

}