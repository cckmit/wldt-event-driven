package it.unimore.wldt.test.event.coap;

import it.unimore.dipi.iot.wldt.event.PhysicalEvent;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 25/05/2021 - 12:19
 */
public class CoapPhysicalAssetResourceMirroredEvent extends PhysicalEvent {

    private String resourceId;

    public CoapPhysicalAssetResourceMirroredEvent() {
    }

    public CoapPhysicalAssetResourceMirroredEvent(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoapResourceMirroredEvent{");
        sb.append("resourceId='").append(resourceId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
