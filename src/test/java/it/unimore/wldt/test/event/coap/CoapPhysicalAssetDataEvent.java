package it.unimore.wldt.test.event.coap;

import it.unimore.dipi.iot.wldt.event.PhysicalEvent;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 25/05/2021 - 12:20
 */
public class CoapPhysicalAssetDataEvent extends PhysicalEvent {

    private String payload;

    public CoapPhysicalAssetDataEvent() {
    }

    public CoapPhysicalAssetDataEvent(String payload) {
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
        final StringBuffer sb = new StringBuffer("CoapPhysicalAssetDataEvent{");
        sb.append("payload='").append(payload).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
