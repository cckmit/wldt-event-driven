package it.unimore.dipi.iot.wldt.processing.step;

import it.unimore.dipi.iot.wldt.processing.PipelineData;
import it.unimore.dipi.iot.wldt.processing.ProcessingStep;
import it.unimore.dipi.iot.wldt.processing.ProcessingStepListener;
import it.unimore.dipi.iot.wldt.processing.cache.PipelineCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Named;
import java.util.Optional;

@Named("IdentityProcessingStep")
public class IdentityProcessingStep implements ProcessingStep {

    private static final Logger logger = LoggerFactory.getLogger(IdentityProcessingStep.class);

    @Override
    public void execute(PipelineCache pipelineCache, PipelineData data, ProcessingStepListener listener) {
        if(listener != null) {
            logger.debug("Executing Identity Processing Step with data: {}", data.toString());
            listener.onStepDone(this, Optional.of(data));
        }
        else
            logger.error("Processing Step Listener or MqttProcessingInfo Data = Null ! Skipping processing step");
    }
}
