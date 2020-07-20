package tapsi.geodoor.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.database.tables.ConfigDao;

public class GeoDoorDbRepository {

    private ConfigDao configDao;
    private LiveData<Config> config;

    public GeoDoorDbRepository(Application application) {
        GeoDoorDb database = GeoDoorDb.getInstance(application);
        configDao = database.configDao();
        config = configDao.getConfig();
    }

    public void insert(Config config) {
        new InsertConfigAsyncTask(configDao).execute(config);
    }

    public void update(Config config) {
        new UpdateConfigAsyncTask(configDao).execute(config);
    }

    public void deleteAll() {
        new DeleteAllConfigAsyncTask(configDao).execute();
    }

    public LiveData<Config> getConfig() {
        return config;
    }

    private static class InsertConfigAsyncTask extends AsyncTask<Config, Void, Void> {

        private ConfigDao configDao;

        public InsertConfigAsyncTask(ConfigDao configDao) {
            this.configDao = configDao;
        }

        @Override
        protected Void doInBackground(Config... configs) {
            configDao.insert(configs[0]);
            return null;
        }
    }

    private static class UpdateConfigAsyncTask extends AsyncTask<Config, Void, Void> {

        private ConfigDao configDao;

        public UpdateConfigAsyncTask(ConfigDao configDao) {
            this.configDao = configDao;
        }

        @Override
        protected Void doInBackground(Config... configs) {
            configDao.update(configs[0]);
            return null;
        }
    }

    private static class DeleteAllConfigAsyncTask extends AsyncTask<Void, Void, Void> {

        private ConfigDao configDao;

        public DeleteAllConfigAsyncTask(ConfigDao configDao) {
            this.configDao = configDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            configDao.deleteAll();
            return null;
        }
    }
}
