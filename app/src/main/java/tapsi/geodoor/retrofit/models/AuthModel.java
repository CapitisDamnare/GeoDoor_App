package tapsi.geodoor.retrofit.models;

public class AuthModel {
    private String name;
    private String md5Hash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public AuthModel() {
    }

    public AuthModel(String name, String md5Hash) {
        this.name = name;
        this.md5Hash = md5Hash;
    }
}
