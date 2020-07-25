package tapsi.geodoor.logic;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import tapsi.geodoor.database.tables.Config;
import tapsi.geodoor.retrofit.RetrofitHandler;
import tapsi.geodoor.retrofit.models.AnswerModel;
import tapsi.geodoor.retrofit.models.AuthModel;
import tapsi.geodoor.retrofit.models.CommandItem;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;
import static tapsi.geodoor.services.LocationUpdatesService.ACTION_BROADCAST;

public class ServerCommunicationHandler implements RetrofitHandler.RetrofitListener {
    private static final String TAG = "tapsi.ServerCommunicationHandler";

    public static final String EXTRA_CONNECTION_FAILURE = PACKAGE_NAME + ".connection_failure";
    public static final String EXTRA_CONNECTION_STATUS = PACKAGE_NAME + ".connection_status";
    public static final String EXTRA_LOGIN_FAILED = PACKAGE_NAME + ".login_failed";
    public static final String EXTRA_REGISTER_DATA = PACKAGE_NAME + ".register_data";
    public static final String EXTRA_REGISTER_FAILED = PACKAGE_NAME + ".register_failed";
    public static final String EXTRA_COMMAND_SUCCESS = PACKAGE_NAME + ".command_success";
    public static final String EXTRA_COMMAND_FAILED = PACKAGE_NAME + ".command_failed";

    private RetrofitHandler retrofitHandler;
    private Context context;

    public enum AccessRights
    {
        Allowed,
        NotAllowed,
        Register
    }

    public ServerCommunicationHandler(RetrofitHandler retrofitHandler, Context context) {
        this.retrofitHandler = retrofitHandler;
        this.retrofitHandler.setOnRetrofitListener(this);
        this.context = context;
    }

    @Override
    public void loginSuccessful() {
        sendBroadcast(EXTRA_CONNECTION_STATUS, true);
    }

    @Override
    public void loginFailed(AnswerModel answerModel) {
        if (answerModel.getData() == null){
            sendBroadcast(EXTRA_CONNECTION_STATUS, false);
            sendBroadcast(EXTRA_LOGIN_FAILED, answerModel.getAnswer());
            return;
        }

        AccessRights accessRights = AccessRights.valueOf(answerModel.getData());
        if (accessRights == AccessRights.Register) {
            retrofitHandler.registerUser();
        } else {
            // Should never happen.
            sendBroadcast(EXTRA_CONNECTION_STATUS, false);
            sendBroadcast(EXTRA_LOGIN_FAILED, answerModel.getAnswer());
        }
    }

    @Override
    public void loginOnFailure(String message) {
        // TODO: How to safe the log in db?
        sendBroadcast(EXTRA_CONNECTION_FAILURE, message);
        sendBroadcast(EXTRA_CONNECTION_STATUS, false);
    }

    @Override
    public void registerSuccessful(AnswerModel answerModel) {
        // This will trigger a configuration update which will trigger another login
        sendBroadcast(EXTRA_REGISTER_DATA, answerModel.getData());
}

    @Override
    public void registerFailed(AnswerModel answerModel) {
        sendBroadcast(EXTRA_REGISTER_FAILED, answerModel.getAnswer());
    }

    @Override
    public void registerOnFailure(String message) {
        sendBroadcast(EXTRA_CONNECTION_FAILURE, message);
        sendBroadcast(EXTRA_CONNECTION_STATUS, false);
    }

    @Override
    public void sendCommandSuccessful(AnswerModel answerModel) {
        sendBroadcast(EXTRA_COMMAND_SUCCESS, answerModel.getAnswer());
    }

    @Override
    public void sendCommandOnFailure(String message) {
        sendBroadcast(EXTRA_COMMAND_FAILED, message);
        sendBroadcast(EXTRA_CONNECTION_STATUS, false);
    }

    public void openGate() {
        CommandItem commandItem = new CommandItem();
        Config config = retrofitHandler.getConfig();
        commandItem.authentication = new AuthModel(config.getName(), config.getMd5Hash());
        commandItem.command = CommandItem.Command.OpenGate;
        commandItem.commandValue = CommandItem.CommandValue.Open;
        retrofitHandler.sendCommand(commandItem);
    }

    public void openGateAuto() {
        CommandItem commandItem = new CommandItem();
        Config config = retrofitHandler.getConfig();
        commandItem.authentication = new AuthModel(config.getName(), config.getMd5Hash());
        commandItem.command = CommandItem.Command.OpenGateAuto;
        commandItem.commandValue = CommandItem.CommandValue.Open;
        retrofitHandler.sendCommand(commandItem);
    }

    public void openDoor() {
        CommandItem commandItem = new CommandItem();
        Config config = retrofitHandler.getConfig();
        commandItem.authentication = new AuthModel(config.getName(), config.getMd5Hash());
        commandItem.command = CommandItem.Command.OpenDoor;
        commandItem.commandValue = CommandItem.CommandValue.Open;
        retrofitHandler.sendCommand(commandItem);
    }

    private void sendBroadcast(String putExtraString, String data) {
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(putExtraString, data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendBroadcast(String putExtraString, boolean data) {
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(putExtraString, data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
