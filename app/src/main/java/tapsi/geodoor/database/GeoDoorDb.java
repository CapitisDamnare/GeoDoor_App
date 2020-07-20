package tapsi.geodoor.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.database.tables.ConfigDao;

@Database(entities = {Config.class}, version = 1)
public abstract class GeoDoorDb extends RoomDatabase {

    private static GeoDoorDb instance;

    public abstract ConfigDao configDao();

    public static synchronized GeoDoorDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GeoDoorDb.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ConfigDao configDao;

        private PopulateDbAsyncTask(GeoDoorDb db) {
            configDao = db.configDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            configDao.insert(new Config(
                    "John Does",
                    "1.1.1.1",
                    "47.01234567",
                    "14.01231122",
                    "424",
                    200,
                    20
            ));
            return null;
        }
    }
}
