package it.unimore.wldt.test.event.coap;

import com.google.common.eventbus.Subscribe;
import it.unimore.dipi.iot.wldt.event.DigitalResponseEvent;
import it.unimore.dipi.iot.wldt.event.WldtEventManager;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.worker.WldtWorker;
import it.unimore.dipi.iot.wldt.worker.dummy.DummyWorkerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class CoapDigitalAdapter2 {

    private String id = UUID.randomUUID().toString();

    private static final Logger logger = LoggerFactory.getLogger(CoapDigitalAdapter2.class);

    public CoapDigitalAdapter2() {
    }

    @Subscribe
    public void onNewPhysicalAssetData(CoapPhysicalAssetDataEvent physicalAssetDataEvent) {
        logger.info("{} -> New Data Event Received: {}", id, physicalAssetDataEvent);
    }

    @Subscribe
    public void onNewPhysicalAssetResourceMirrored(CoapPhysicalAssetResourceMirroredEvent resourceMirroredEvent) {
        logger.info("{} -> New Resource Mirrored Event Received: {}", id, resourceMirroredEvent);
    }

}
