package it.unimore.dipi.iot.wldt.worker;


import it.unimore.dipi.iot.wldt.cache.IWldtCache;
import it.unimore.dipi.iot.wldt.event.PhysicalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 24/05/2021 - 15:08
 */
public abstract class DigitalAdapter<C, K, V> extends WldtWorker<C, K, V>{

    private static final Logger logger = LoggerFactory.getLogger(DigitalAdapter.class);

    public DigitalAdapter(IWldtCache<K,V> workerCache){
        super(workerCache);
    }

    public DigitalAdapter(C wldtWorkerConfiguration, IWldtCache<K,V>  workerCache){
        super(wldtWorkerConfiguration, workerCache);
    }

    public DigitalAdapter(C wldtWorkerConfiguration){
        super(wldtWorkerConfiguration);
    }

    protected void publishDigitalDataEvent(PhysicalEvent physicalEvent){

    }
}
