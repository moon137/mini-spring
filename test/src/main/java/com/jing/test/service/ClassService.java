package com.jing.test.service;

import com.jing.framework.beans.AutoWired;
import com.jing.framework.beans.Bean;
import com.jing.test.dao.ClassDao;

/**
 * @author GUO
 * @date 2019/9/25
 */
@Bean
public class ClassService {
    @AutoWired
    ClassDao classDao;
    // 简单的测试了一下次序问题
    @AutoWired
    StudentService studentService;
    public String getClass(String className) {
        System.out.println(studentService.getStudent("从班级里调用的StudentService"));
        return classDao.findClass(className);
    }
}
