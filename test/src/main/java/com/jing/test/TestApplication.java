package com.jing.test;

import com.jing.framework.starter.MiniApplication;

/**
 * @author GUO
 * @date 2019/9/24
 */
public class TestApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MiniApplication.run(TestApplication.class,args);
    }
}
