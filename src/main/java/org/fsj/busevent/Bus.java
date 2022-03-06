package org.fsj.busevent;


import java.io.Closeable;

/**
 * bus定义EventBus所使用的方法
 */
public interface Bus extends Closeable {

    /**
     * 将某个对象注册到bus上面，之后此类就成为Subscriber
     * @param subscriber
     */
    void register(Object subscriber);


    /**
     * 将某个对象从bus上面解绑，取消注册之后就不会再接受来自bug的任何消息
     * @param subscriber
     */
    void unregister(Object subscriber);

    /**
     * 提交event到默认topic
     * @param event
     */
    void post(Object event);


    /**
     * 提供event到指定的topic
     * @param event
     * @param topic
     */
    void post(Object event,String topic);

    /**
     * 获取Bus的名字标识
     * @return
     */
    String  getBusName();

}
