package tapsi.geodoor.retrofit.models;

public class DeviceModel {

    private AuthModel auth;
    private String device;

    public AuthModel getAuth() {
        return auth;
    }

    public void setAuth(AuthModel auth) {
        this.auth = auth;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public DeviceModel() {
    }

    public DeviceModel(AuthModel auth, String device) {
        this.auth = auth;
        this.device = device;
    }
}
