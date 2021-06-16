package it.unimore.wldt.test.event;

import it.unimore.dipi.iot.wldt.engine.WldtConfiguration;
import it.unimore.dipi.iot.wldt.engine.WldtEngine;
import it.unimore.dipi.iot.wldt.processing.ProcessingPipeline;
import it.unimore.dipi.iot.wldt.processing.step.IdentityProcessingStep;
import it.unimore.dipi.iot.wldt.worker.MirroringListener;
import it.unimore.dipi.iot.wldt.worker.dummy.DummyWorkerConfiguration;
import it.unimore.dipi.iot.wldt.worker.mqtt.Mqtt2MqttConfiguration;
import it.unimore.dipi.iot.wldt.worker.mqtt.Mqtt2MqttWorker;
import it.unimore.dipi.iot.wldt.worker.mqtt.MqttTopicDescriptor;
import it.unimore.wldt.test.mqtt.MqttAverageProcessingStep;
import it.unimore.wldt.test.mqtt.MqttCommandTopicChangeStep;
import it.unimore.wldt.test.mqtt.MqttPayloadChangeStep;
import it.unimore.wldt.test.mqtt.MqttTopicChangeStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class DummyEventProcess {

    private static final String TAG = "[WLDT-Event-Process]";

    private static final Logger logger = LoggerFactory.getLogger(DummyEventProcess.class);

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

            wldtEngine.addNewWorker(new DummyPhysicalAdapter());
            wldtEngine.addNewWorker(new DummyDigitalAdapter());
            wldtEngine.startWorkers();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

