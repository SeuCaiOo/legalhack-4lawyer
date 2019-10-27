package br.com.app4lawyer.data.local.database

import androidx.room.*

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: AppEntity)

    @Query("DELETE FROM app_table WHERE nome = :nome")
    fun deleteByName(nome: String)

    @Transaction
    fun update(entity: AppEntity) {
        deleteByName(entity.name)
        insert(entity)
    }

    @Query("SELECT * FROM app_table WHERE nome = :nome ")
    fun getByName(nome: String): AppEntity?

}