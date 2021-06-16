package it.unimore.wldt.test.event.coap;

import com.google.common.eventbus.Subscribe;
import it.unimore.dipi.iot.wldt.cache.IWldtCache;
import it.unimore.dipi.iot.wldt.event.InternalEvent;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.worker.WldtWorker;
import it.unimore.dipi.iot.wldt.worker.dummy.DummyWorkerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class CoapPhysicalAdapter extends WldtWorker<DummyWorkerConfiguration, String, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CoapPhysicalAdapter.class);

    private Random random = null;

    private int RUN_COUNT_LIMIT = 10000;

    public CoapPhysicalAdapter() {
        this.random = new Random();
    }

    public CoapPhysicalAdapter(IWldtCache<String, Integer> wldtCache) {
        super(wldtCache);
    }

    @Override
    public void start() throws WldtConfigurationException, WldtRuntimeException {

        try{
            emulateDiscovery();
            for(int i = 0; i < RUN_COUNT_LIMIT; i++)
                emulateObserveCoapResource(i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws WldtRuntimeException {

    }

    @Subscribe
    private void onReceiveDigitalCoapRequestEvent(CoapDigitalRequestEvent requestEvent){

        logger.debug("Received CoapDigitalRequestEvent: {}", requestEvent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    logger.debug("Received CoapDigitalRequestEvent: {}", requestEvent);
                    logger.debug("Sleeping to emulate the request's management ...");
                    Thread.sleep(4000);

                    broadcastDigitalTwinInternalEvent(new CoapDigitalResponseEvent(requestEvent.getId(), "HELLO WORLD !"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void emulateDiscovery(){
        try{
            Thread.sleep(5000);
            this.broadcastDigitalTwinInternalEvent(new CoapPhysicalAssetResourceMirroredEvent("randomIntegerResource"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void emulateObserveCoapResource(int roundIndex) {

        try{

            logger.info("Round [{}]: Dummy Worker Incoming Get Request .... ", roundIndex);

            int physicalObjectValue = 0;

            physicalObjectValue = retrieveValueFromPhysicalObject();
            logger.info("Round [{}]: Physical Object Value: {} ", roundIndex, physicalObjectValue);

            //Broadcast a new Internal Digital Twin Event
            //TODO Change with the appropriate method (according to the type)
            this.broadcastDigitalTwinInternalEvent(new CoapPhysicalAssetDataEvent(String.valueOf(physicalObjectValue)));

            Thread.sleep(random.nextInt(3000) + 1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int retrieveValueFromPhysicalObject(){
        try{
            Thread.sleep(random.nextInt(1000) + 100);
            return random.nextInt(3000);
        }catch (Exception e){
            logger.error("Error getting random mockup value ! Error: {}", e.getLocalizedMessage());
            return 0;
        }
    }

}
