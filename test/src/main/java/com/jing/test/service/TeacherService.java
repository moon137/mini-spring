package com.jing.test.service;

import com.jing.framework.beans.AutoWired;
import com.jing.framework.beans.Bean;
import com.jing.test.dao.ClassDao;
import com.jing.test.dao.StudentDao;
import com.jing.test.dao.TeacherDao;

/**
 * @author GUO
 * @date 2019/9/25
 */
@Bean
public class TeacherService {
    @AutoWired
    ClassDao classDao;
    @AutoWired
    StudentDao studentDao;
    @AutoWired
    TeacherDao teacherDao;
    public String getTeacher(String teacherName) {
        String teacher = teacherDao.getTeacher(teacherName);
        String clazz = classDao.findClass("2班");
        String stu = studentDao.getStudent();
        return teacher+clazz+stu+"是三好学生。";
    }
}
