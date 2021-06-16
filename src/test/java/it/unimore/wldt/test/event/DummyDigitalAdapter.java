package it.unimore.wldt.test.event;

import com.google.common.eventbus.Subscribe;
import it.unimore.dipi.iot.wldt.event.InternalEvent;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.worker.WldtWorker;
import it.unimore.dipi.iot.wldt.worker.dummy.DummyWorkerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyDigitalAdapter extends WldtWorker<DummyWorkerConfiguration, String, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(DummyDigitalAdapter.class);

    private static final String METRIC_BASE_IDENTIFIER = "dummy_digital_adapter";

    private static final String WORKER_EXECUTION_TIME_METRICS_FIELD = "execution_time";

    private static final String WORKER_VALUE_METRICS_FIELD = "execution_value";

    private static final String CACHE_VALUE_KEY = "physical_obj_value";

    public static final String DEFAULT_PROCESSING_PIPELINE = "default_processing_pipeline";

    private String wldtId = null;

    private int RUN_COUNT_LIMIT = 10000;

    public DummyDigitalAdapter() {
    }

    @Override
    public void start() throws WldtConfigurationException, WldtRuntimeException {
        try{
        }catch (Exception e){
            throw new WldtRuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public void stop() throws WldtRuntimeException {
        //TODO Implement stop method ...
    }

    @Subscribe
    public void onInternalDigitalTwinEvent(InternalEvent event) {
        logger.info("New Event Received: {}", event);
    }

}
