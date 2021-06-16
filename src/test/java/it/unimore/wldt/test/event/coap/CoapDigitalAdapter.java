package it.unimore.wldt.test.event.coap;

import com.google.common.eventbus.Subscribe;
import it.unimore.dipi.iot.wldt.event.DigitalResponseEvent;
import it.unimore.dipi.iot.wldt.event.InternalEvent;
import it.unimore.dipi.iot.wldt.event.WldtEventManager;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.worker.WldtWorker;
import it.unimore.dipi.iot.wldt.worker.dummy.DummyWorkerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class CoapDigitalAdapter extends WldtWorker<DummyWorkerConfiguration, String, Integer> {

    private String id = UUID.randomUUID().toString();

    private static final Logger logger = LoggerFactory.getLogger(CoapDigitalAdapter.class);

    public CoapDigitalAdapter() {
    }

    @Override
    public void start() throws WldtConfigurationException, WldtRuntimeException {
        try{
            WldtEventManager.getInstance().getInternalEventBus().register(this);
            emulateIncomingGetRequest();
        }catch (Exception e){
            throw new WldtRuntimeException(e.getLocalizedMessage());
        }
    }

    private void emulateIncomingGetRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Sleep before emulating the random external request
                    Thread.sleep(20000);
                    logger.debug("!!!! WAITING FOR THE RESPONSE !!!!");
                    Optional<DigitalResponseEvent> response = handleRequestEvent(new CoapDigitalRequestEvent("GET", "/test", null));
                    if(response.isPresent())
                        logger.info("Received Response: {}", response);
                    else
                        logger.error("ERROR ! EMPTY RESPONSE RECEIVED !");

                    logger.debug("!!!! DONE !!!!");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() throws WldtRuntimeException {
        //TODO Implement stop method ...
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
