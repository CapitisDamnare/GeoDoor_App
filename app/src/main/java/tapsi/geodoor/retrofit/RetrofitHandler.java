package tapsi.geodoor.retrofit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.retrofit.models.AnswerModel;
import tapsi.geodoor.retrofit.models.AuthModel;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static tapsi.geodoor.services.LocationUpdatesService.ACTION_BROADCAST;

public class RetrofitHandler {
    private static final String TAG = "tapsi.retrofit";
    public static final String EXTRA_REGISTER_DATA = PACKAGE_NAME + ".register_data";

    private Config config = null;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Context context;

    public AnswerModel parseError(Response<?> response) {
        Converter<ResponseBody, AnswerModel> converter = retrofit
                        .responseBodyConverter(AnswerModel.class, new Annotation[0]);

        AnswerModel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new AnswerModel();
        }

        return error;
    }

    public void initHandler(Config config, Context context) {
        this.config = config;
        this.context = context;

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(config.getIpAddress())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public void registerUser() {
        if (retrofit == null)
            return;

        AuthModel auth = new AuthModel();
        auth.setName(config.getName());

        Call<AnswerModel> call = jsonPlaceHolderApi.registerUser(auth);

        call.enqueue(new Callback<AnswerModel>() {
            @Override
            public void onResponse(Call<AnswerModel> call, Response<AnswerModel> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "registerUser response: " + response
                            + "\nbody: " + response.body());

                    Intent intent = new Intent(ACTION_BROADCAST);
                    intent.putExtra(EXTRA_REGISTER_DATA, response.body().getData());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
                else {
                    Log.i(TAG, "registerUser response: " + response
                            + "\nerrorBody: " + parseError(response));
                    // TODO: Send couldn't login data.
                }
            }

            @Override
            public void onFailure(Call<AnswerModel> call, Throwable t) {
                Log.e(TAG, "OnFailure:\n" + t.getMessage());
                // TODO: Send couldn't login
            }
        });
    }

    public void loginUser() {
        if (retrofit == null)
            return;

        AuthModel auth = new AuthModel();
        auth.setName(config.getName());
        auth.setMd5Hash(config.getMd5Hash());

        Call<AnswerModel> call = jsonPlaceHolderApi.loginUser(auth);

        call.enqueue(new Callback<AnswerModel>() {
            @Override
            public void onResponse(Call<AnswerModel> call, Response<AnswerModel> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "loginUser response: " + response
                            + "\nbody: " + response.body());
                    // TODO: Start getGate request
                }
                else {
                    Log.i(TAG, "loginUser response: " + response
                            + "\nerrorBody: " + parseError(response));
                    registerUser();
                }
            }

            @Override
            public void onFailure(Call<AnswerModel> call, Throwable t) {
                Log.e(TAG, "OnFailure:\n" + t.getMessage());
                // TODO: Send couldn't login
            }
        });
    }
}
