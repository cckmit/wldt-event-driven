package it.unimore.dipi.iot.wldt.event;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project wldt-core
 * @created 24/05/2021 - 15:43
 */
public class WldtEventManager {

    private static WldtEventManager instance;

    private EventBus internalEventBus;

    private WldtEventManager(){
        //this.internalEventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        this.internalEventBus = new EventBus();
    }

    public static WldtEventManager getInstance(){
        if(instance == null)
            instance = new WldtEventManager();

        return instance;
    }

    public EventBus getInternalEventBus() {
        return internalEventBus;
    }
}
