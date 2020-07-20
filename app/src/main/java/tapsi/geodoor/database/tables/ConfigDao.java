package tapsi.geodoor.database.tables;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ConfigDao {

    @Insert
    void insert (Config config);

    @Update
    void update (Config config);

    @Delete
    void delete (Config config);

    @Query("DELETE FROM config_table")
    void deleteAll ();

    @Query("SELECT * FROM  config_table WHERE id = 1")
    LiveData<Config> getConfig();

    @Query("SELECT * FROM  config_table WHERE id = 1")
    Config getSyncConfig();
}
