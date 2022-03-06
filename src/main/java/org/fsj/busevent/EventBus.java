package org.fsj.busevent;

import java.io.IOException;

public class EventBus implements Bus{

    private final Registry registry ;
    private final Dispatcher dispatch;

    private String busName;
    public static final String  DEFAULT_BUS_NAME = "default";
    public static final String  DEFAULT_TOPIC = "default-topic";


    @Override
    public void register(Object subscriber) {

    }

    @Override
    public void unregister(Object subscriber) {

    }

    @Override
    public void post(Object event) {

    }

    @Override
    public void post(Object event, String topic) {

    }

    @Override
    public String getBusName() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
