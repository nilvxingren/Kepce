package tr.com.kepce.address;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class AddressSavedEvent extends ResponseEvent<Void> {

    public AddressSavedEvent(KepceResponse<Void> response) {
        super(response);
    }
}
