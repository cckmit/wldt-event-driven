package it.unimore.dipi.iot.wldt.processing;

import java.util.Optional;

public interface ProcessingPipelineListener {

    public void onPipelineDone(Optional<PipelineData> result);

    public void onPipelineError();

}
