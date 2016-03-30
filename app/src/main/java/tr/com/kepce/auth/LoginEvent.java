package tr.com.kepce.auth;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class LoginEvent extends ResponseEvent<String> {

    private String mEmail;

    public LoginEvent(String email, KepceResponse<String> response) {
        super(response);
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
