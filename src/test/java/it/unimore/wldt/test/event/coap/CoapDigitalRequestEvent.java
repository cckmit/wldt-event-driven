package it.unimore.wldt.test.event.coap;

import it.unimore.dipi.iot.wldt.event.DigitalRequestEvent;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 25/05/2021 - 12:23
 */
public class CoapDigitalRequestEvent extends DigitalRequestEvent {

    private String method;

    private String path;

    private String payload = null;

    public CoapDigitalRequestEvent() {
    }

    public CoapDigitalRequestEvent(String method, String path, String payload) {
        this.method = method;
        this.path = path;
        this.payload = payload;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoapDigitalRequestEvent{");
        sb.append("id='").append(id).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
