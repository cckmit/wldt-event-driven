package it.unimore.wldt.test.event.coap;

import it.unimore.dipi.iot.wldt.event.DigitalResponseEvent;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 25/05/2021 - 17:18
 */
public class CoapDigitalResponseEvent extends DigitalResponseEvent {

    private String payload;

    public CoapDigitalResponseEvent(String requestEventId) {
        super(requestEventId);
    }

    public CoapDigitalResponseEvent(String requestEventId, String payload) {
        super(requestEventId);
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoapDigitalResponseEvent{");
        sb.append("id='").append(id).append('\'');
        sb.append(", requestEventId='").append(requestEventId).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
