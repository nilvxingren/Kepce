package tr.com.kepce.address;

import java.util.List;

import tr.com.kepce.common.KepceResponse;
import tr.com.kepce.common.ResponseEvent;

public class AddressesLoadedEvent extends ResponseEvent<List<Address>> {

    public AddressesLoadedEvent(KepceResponse<List<Address>> response) {
        super(response);
    }
}