package tr.com.kepce.profile;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class ProfileLoadedEvent extends ResponseEvent<User> {

    public ProfileLoadedEvent(KepceResponse<User> response) {
        super(response);
    }
}
