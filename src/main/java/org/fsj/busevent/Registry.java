package org.fsj.busevent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 功能注册 主要将Subscriber类中被Subscribe注解修饰的方法填充到数据subscriberContainer中
 */
public class Registry {

    /**
     * 存储Subscriber集合和Topic之间的关系
     */
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();


    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic){
        return subscriberContainer.get(topic);
    }


    public void bind(Object subscriber){
        //获取subscriber object的方法集合进行绑定
        List<Method> methods = getSubscribeMethod( subscriber);
        methods.forEach(m->tierSubscriber(subscriber,m));
    }
    public void unbind(Object subscriber){
        //unbind为了提高速度只对subscriber失效
        subscriberContainer.forEach((key,queue)->{
            queue.forEach(s->{
                if (s.getSubscribeObjet()==subscriber){
                    s.setDisable(true);
                }
            });
        });
    }

    private void  tierSubscriber(Object subscriber,Method method ){
        final  Subscribe subscribe = method.getAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        //当topic没有Subscriber Queue的时候创建一个 jdk8新方法
        subscriberContainer.computeIfAbsent(topic,key ->new ConcurrentLinkedQueue<>());
        //创建一个Subscriber并加入Subscriber列表
        subscriberContainer.get(topic).add(new Subscriber(subscriber,method));
    }

    /**
     *
     * @param subscriber
     * @return
     */
    private List<Method> getSubscribeMethod(Object subscriber){
        final List<Method> methodList = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        //不断获取当前类和父类的所有@Subscriber方法
        while (temp!=null){
            Method[] declaredMethods = temp.getDeclaredMethods();
            //只有public && 有一个入参 && 被@Subscriber标记的方法才符合回掉方法
            Arrays.stream(declaredMethods).filter(m->m.isAnnotationPresent(Subscribe.class)
                    && m.getParameterCount()==1 && m.getModifiers() == Modifier.PUBLIC)
                    .forEach(methodList::add);
            temp = temp.getSuperclass();

        }
        return methodList;

    }
}
