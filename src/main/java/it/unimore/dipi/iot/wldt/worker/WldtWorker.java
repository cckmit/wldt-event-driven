package it.unimore.dipi.iot.wldt.worker;

import com.google.common.eventbus.Subscribe;
import it.unimore.dipi.iot.wldt.cache.IWldtCache;
import it.unimore.dipi.iot.wldt.event.*;
import it.unimore.dipi.iot.wldt.exception.ProcessingPipelineException;
import it.unimore.dipi.iot.wldt.exception.WldtConfigurationException;
import it.unimore.dipi.iot.wldt.exception.WldtRuntimeException;
import it.unimore.dipi.iot.wldt.exception.WldtWorkerException;
import it.unimore.dipi.iot.wldt.processing.IProcessingPipeline;
import it.unimore.dipi.iot.wldt.processing.PipelineData;
import it.unimore.dipi.iot.wldt.processing.ProcessingPipelineListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Author: Marco Picone, Ph.D. (marco.picone@unimore.it)
 * Date: 27/03/2020
 * Project: White Label Digital Twin Java Framework - (whitelabel-digitaltwin)
 */
public abstract class WldtWorker<C, K, V> implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WldtWorker.class);

    private C wldtWorkerConfiguration;

    protected IWldtCache<K,V> workerCache = null;

    private Map<String, IProcessingPipeline> processingPipelineMap = null;

    private List<MirroringListener> mirroringListenerList = null;

    private Map<String, Object> responseTransactionMap = null;

    public WldtWorker(){
        this.responseTransactionMap = new HashMap<>();
    }

    public WldtWorker(IWldtCache<K,V>  workerCache){
        this.workerCache = workerCache;
        this.responseTransactionMap = new HashMap<>();
    }

    public WldtWorker(C wldtWorkerConfiguration, IWldtCache<K,V>  workerCache){
        this.wldtWorkerConfiguration = wldtWorkerConfiguration;
        this.workerCache = workerCache;
        this.responseTransactionMap = new HashMap<>();
    }

    public WldtWorker(C wldtWorkerConfiguration){
        this.wldtWorkerConfiguration = wldtWorkerConfiguration;
        this.responseTransactionMap = new HashMap<>();
    }

    @Override
    public void run() {

        try {
            initCache();
            start();
        } catch (Exception e) {
            logger.error("WLDT WORKER ERROR: {}", e.getLocalizedMessage());
        }
    }

    private void initCache() {
        try{

            if(this.workerCache != null) {
                this.workerCache.initCache();
                logger.info("Worker Cache properly initialized !");
            }
            else
                logger.warn("Worker Cache = null ! Cache init skipped");

        }catch (Exception e){
            logger.error("Error Initializing Worker Cache ! Error: {}", e.getLocalizedMessage());
        }
    }

    public C getWldtWorkerConfiguration() {
        return wldtWorkerConfiguration;
    }

    public void setWldtWorkerConfiguration(C wldtWorkerConfiguration) {
        this.wldtWorkerConfiguration = wldtWorkerConfiguration;
    }

    public boolean hasProcessingPipeline(String processingPipelineId) {
        if(this.processingPipelineMap != null)
            return this.processingPipelineMap.containsKey(processingPipelineId);
        else
            return false;
    }

    public void addProcessingPipeline(String processingPipelineId, IProcessingPipeline newProcessingPipeline) throws ProcessingPipelineException {

        try{

            if(this.processingPipelineMap == null)
                this.processingPipelineMap = new HashMap<>();

            this.processingPipelineMap.put(processingPipelineId, newProcessingPipeline);

        }catch (Exception e){
            logger.error("Error adding processing pipeline ({}) ! Error: {}", processingPipelineId, e.getLocalizedMessage());
            throw new ProcessingPipelineException(e.getLocalizedMessage());
        }
    }

    public void removeProcessingPipeline(String processingPipelineId, IProcessingPipeline newProcessingPipeline) throws ProcessingPipelineException {
        try{
            if(this.processingPipelineMap != null)
                this.processingPipelineMap.remove(processingPipelineId);
        }catch (Exception e){
            logger.error("Error removing processing pipeline ({}) ! Error: {}", processingPipelineId, e.getLocalizedMessage());
            throw new ProcessingPipelineException(e.getLocalizedMessage());
        }
    }

    public void executeProcessingPipeline(String processingPipelineId, PipelineData initialData, ProcessingPipelineListener listener) throws ProcessingPipelineException {

        if(processingPipelineId != null
                && this.processingPipelineMap != null
                && this.processingPipelineMap.containsKey(processingPipelineId)
                && this.processingPipelineMap.get(processingPipelineId) != null){
            this.processingPipelineMap.get(processingPipelineId).start(initialData, listener);
        }
        else
            throw new ProcessingPipelineException("PipelineId or ProcessingPipeline = Null or Not Found !");
    }

    public void addMirroringListener(MirroringListener mirroringListener) throws WldtWorkerException {

        try {

            if(this.mirroringListenerList == null)
                this.mirroringListenerList = new ArrayList<>();

            if(mirroringListener != null)
                this.mirroringListenerList.add(mirroringListener);
            else
                throw new WldtWorkerException("Error adding MirroringListener ! Provided Object = null !");

        }catch (Exception e){
            logger.error("Error adding mirroring listener ! Error: {}", e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }

    public void removeMirroringListener(MirroringListener mirroringListener) throws WldtWorkerException {

        try {

            if(this.mirroringListenerList != null)
                if(mirroringListener != null)
                    this.mirroringListenerList.remove(mirroringListener);
                else
                    throw new WldtWorkerException("Error removing MirroringListener ! Provided Object = null !");
        }catch (Exception e){
            logger.error("Error adding mirroring listener ! Error: {}", e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }

    public void notifyDeviceMirrored(String deviceId, Map<String, Object> metadata) throws WldtWorkerException{

        try{

            if(this.mirroringListenerList != null)
                this.mirroringListenerList.forEach(mirroringListener -> {
                    mirroringListener.onDeviceMirrored(deviceId, metadata);
                });

        }catch (Exception e){
            logger.error("Error notifying listener ! Error: {}", e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }

    public void notifyResourceMirrored(String deviceId, Map<String, Object> metadata)  throws WldtWorkerException{
        try{

            if(this.mirroringListenerList != null)
                this.mirroringListenerList.forEach(mirroringListener -> {
                    mirroringListener.onResourceMirrored(deviceId, metadata);
                });

        }catch (Exception e){
            logger.error("Error notifying listener ! Error: {}", e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }

    public void notifyDeviceMirroringError(String deviceId, String errorMsg) {
        try{

            if(this.mirroringListenerList != null)
                this.mirroringListenerList.forEach(mirroringListener -> {
                    mirroringListener.onDeviceMirroringError(deviceId, errorMsg);
                });

        }catch (Exception e){
            logger.error("Error notifying listener ! Error: {}", e.getLocalizedMessage());
        }
    }

    public void notifyResourceMirroringError(String deviceId, String errorMsg) {
        try{

            if(this.mirroringListenerList != null)
                this.mirroringListenerList.forEach(mirroringListener -> {
                    mirroringListener.onResourceMirroringError(deviceId, errorMsg);
                });

        }catch (Exception e){
            logger.error("Error notifying listener ! Error: {}", e.getLocalizedMessage());
        }
    }

    protected Optional<DigitalResponseEvent> handleRequestEvent(DigitalRequestEvent digitalRequestEvent) throws WldtWorkerException {

        if(digitalRequestEvent == null || digitalRequestEvent.getId() == null)
            throw new WldtWorkerException("DigitalRequestEvent == null or empty request Id !");

        try{

            this.responseTransactionMap.put(digitalRequestEvent.getId(), new Object());
            Object syncObject = responseTransactionMap.get(digitalRequestEvent.getId());

            CompletableFuture<DigitalResponseEvent> future = CompletableFuture.supplyAsync(new Supplier<DigitalResponseEvent>() {
                @Override
                public DigitalResponseEvent get() {
                    try {
                        //Wait until the response is not yet received
                        synchronized(syncObject) {
                                // Calling wait() will block this thread until another thread
                                // calls notify() on the object.
                                syncObject.wait();
                        }
                    } catch (Exception e) {
                        logger.error("CompletableFeature Exception ! Error: {}", e.getLocalizedMessage());
                        return null;
                    }

                    return new DigitalResponseEvent(digitalRequestEvent.getId());
                }
            });

            logger.debug("Publishing Event: {}", digitalRequestEvent);
            broadcastDigitalTwinInternalEvent(digitalRequestEvent);

            logger.debug("Waiting for request event management for request: {}", digitalRequestEvent);
            DigitalResponseEvent result = future.get(2, TimeUnit.SECONDS);
            logger.debug("Response received: {} for request: {}", result, digitalRequestEvent);
            return Optional.of(result);

        }catch (Exception e){
            logger.error("Error handling request event ! Error: {}/{}", e.getClass(), e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    protected void broadcastDigitalTwinInternalEvent(WldtEvent wldtEvent) throws WldtWorkerException {

        try{
            logger.debug("Broadcasting DT Internal Event .... {}", wldtEvent);
            WldtEventManager.getInstance().getInternalEventBus().post(wldtEvent);
        }catch (Exception e){
            logger.error("Error Broadcasting DT Internal Event: {} ! Error: {}", wldtEvent, e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }

    /*
    protected void registerForDigitalTwinInternalEvents() throws WldtWorkerException {
        try{
            logger.debug("Registering to receive DT Internal Event ....");
            WldtEventManager.getInstance().getInternalEventBus().register(this);
        }catch (Exception e){
            logger.error("Error Registering to receive DT Internal Event .... ! Error: {}", e.getLocalizedMessage());
            throw new WldtWorkerException(e.getLocalizedMessage());
        }
    }
    */

    @Subscribe
    private void onResponseEventReceived(DigitalResponseEvent digitalResponseEvent) {

        logger.debug("Response Event Received: {}", digitalResponseEvent);

        try{

            if(digitalResponseEvent == null || digitalResponseEvent.getId() == null || digitalResponseEvent.getRequestEventId() == null)
                logger.error("DigitalResponseEvent == null or empty request Id !");
            else {
                if(responseTransactionMap.containsKey(digitalResponseEvent.getRequestEventId())){
                    logger.debug("Notify waiting thread ...");
                    Object syncObject = responseTransactionMap.get(digitalResponseEvent.getRequestEventId());
                    synchronized(syncObject) {
                        syncObject.notify();
                        responseTransactionMap.remove(digitalResponseEvent.getRequestEventId());
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    abstract public void start() throws WldtConfigurationException, WldtRuntimeException;

    abstract public void stop() throws WldtRuntimeException;
}
