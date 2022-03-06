package org.fsj.busevent;

import java.lang.reflect.Method;


public class Subscriber {
    private final Object subscribeObjet;
    private final Method subscribeMethod;
    private boolean disable = false;

    public Subscriber(Object subscribeObjet, Method subscribeMethod) {
        this.subscribeObjet = subscribeObjet;
        this.subscribeMethod = subscribeMethod;
    }

    public Object getSubscribeObjet() {
        return subscribeObjet;
    }

    public Method getSubscribeMethod() {
        return subscribeMethod;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
