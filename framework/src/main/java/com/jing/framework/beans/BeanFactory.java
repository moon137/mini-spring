package com.jing.framework.beans;

import com.jing.framework.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GUO
 * @date 2019/9/25
 */
public class BeanFactory {
    public static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    /**
     * 这里的循环要好好理一理执行过程
     * @param classes
     * @throws Exception
     */
    public static void intBean(List<Class<?>> classes) throws Exception {
        List<Class<?>> toCreated = new ArrayList<>(classes);
        while (toCreated.size()!=0){
            int remainSize = toCreated.size();
            for (int i = 0; i < toCreated.size(); i++) {
                if(finishCreate(toCreated.get(i))){
                    System.out.println("成功注入：" + toCreated.get(i));
                    toCreated.remove(i);
                }
            }
            if(toCreated.size()==remainSize){
                throw  new Exception("禁止循环引用依赖");
            }
        }
    }

    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if(!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)){
            return  true;
        }
        Object bean = cls.newInstance();
        // 原视频将BeanFactory.classToBean.put(cls,bean); 放在return true之前。
        BeanFactory.classToBean.put(cls,bean);
        // 检查field,实现注入功能
        for (Field declaredField : cls.getDeclaredFields()) {
            if(declaredField.isAnnotationPresent(AutoWired.class)){
                Class<?> fieldType = declaredField.getType();
                //判断是否创建过
                Object reliantBean = BeanFactory.getBean(fieldType);
                if(reliantBean==null){
                    return false;
                }
                declaredField.setAccessible(true);
                declaredField.set(bean,reliantBean);
            }
        }
//        BeanFactory.classToBean.put(cls,bean);
        return true;
    }
}
