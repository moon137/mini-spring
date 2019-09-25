package com.jing.test.service;

import com.jing.framework.beans.Bean;

/**
 * @author GUO
 * @date 2019/9/25
 */
@Bean
public class StudentService {
    public String getStudent(String studentName) {
        return studentName+"是三好学生";
    }
}
