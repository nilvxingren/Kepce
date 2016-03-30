package tr.com.kepce.auth;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;
import tr.com.kepce.profile.User;

public class RegisterEvent extends ResponseEvent<User> {

    public RegisterEvent(KepceResponse<User> response) {
        super(response);
    }
}
