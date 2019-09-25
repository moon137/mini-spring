package com.jing.test.dao;

import com.jing.framework.beans.Bean;

/**
 * @author GUO
 * @date 2019/9/25
 */
@Bean
public class TeacherDao {
    public String getTeacher(String teacherName) {
        return teacherName+"老师35岁。";
    }
}
