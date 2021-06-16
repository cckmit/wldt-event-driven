package it.unimore.dipi.iot.wldt.event;

import java.util.UUID;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 25/05/2021 - 13:44
 */
public class DigitalResponseEvent extends WldtEvent {

    protected String id = UUID.randomUUID().toString();

    protected String requestEventId;

    public DigitalResponseEvent(String requestEventId) {
        this.requestEventId = requestEventId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestEventId() {
        return requestEventId;
    }

    public void setRequestEventId(String requestEventId) {
        this.requestEventId = requestEventId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseEvent{");
        sb.append("id='").append(id).append('\'');
        sb.append(", requestEventId='").append(requestEventId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
