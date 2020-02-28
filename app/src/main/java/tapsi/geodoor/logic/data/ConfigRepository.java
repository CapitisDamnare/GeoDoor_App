package tapsi.geodoor.logic.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class ConfigRepository {

    private ConfigDao configDao;
    private LiveData<Config> config;
    private Config thisConfig;

    public ConfigRepository(Application application) {
        Database database = Database.getInstance(application);
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

    public Config getThisConfig() {
        return configDao.getThisConfig();
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
