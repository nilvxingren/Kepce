package tr.com.kepce.profile;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class ProfileUpdatedEvent extends ResponseEvent<Void> {

    public ProfileUpdatedEvent(KepceResponse<Void> response) {
        super(response);
    }
}
