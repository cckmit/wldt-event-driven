package it.unimore.wldt.test.event.coap;

import it.unimore.dipi.iot.wldt.engine.WldtConfiguration;
import it.unimore.dipi.iot.wldt.engine.WldtEngine;
import it.unimore.dipi.iot.wldt.event.WldtEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoapDummyWldtProcess {

    private static final String TAG = "[WLDT-Event-Process]";

    private static final Logger logger = LoggerFactory.getLogger(CoapDummyWldtProcess.class);

    public static void main(String[] args)  {

        try{

            logger.info("{} Initializing WLDT-Engine ... ", TAG);

            //Example loading everything from the configuration file
            //WldtEngine wldtEngine = new WldtEngine();
            //wldtEngine.startWorkers();

            //Manual creation of the WldtConfiguration
            WldtConfiguration wldtConfiguration = new WldtConfiguration();
            wldtConfiguration.setDeviceNameSpace("it.unimore.dipi.things");
            wldtConfiguration.setWldtBaseIdentifier("wldt");
            wldtConfiguration.setWldtStartupTimeSeconds(10);
            wldtConfiguration.setApplicationMetricsEnabled(false);

            WldtEngine wldtEngine = new WldtEngine(wldtConfiguration);


            //WldtEventManager.getInstance().getInternalEventBus().register(new CoapDigitalAdapter2());
            //WldtEventManager.getInstance().getInternalEventBus().register(new CoapDigitalAdapter2());
            wldtEngine.addNewWorker(new CoapDigitalAdapter());
            //wldtEngine.addNewWorker(new CoapDigitalAdapter());
            wldtEngine.addNewWorker(new CoapPhysicalAdapter());
            wldtEngine.startWorkers();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

