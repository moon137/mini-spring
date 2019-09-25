package com.jing.test.dao;

import com.jing.framework.beans.Bean;

/**
 * @author GUO
 * @date 2019/9/25
 */
@Bean
public class ClassDao {
    public String findClass(String className) {
        return String.format("班级[%s]在5楼有10人。",className);
    }
}
