package com.jing.test.controllers;

import com.jing.framework.beans.AutoWired;
import com.jing.framework.web.mvc.Controller;
import com.jing.framework.web.mvc.RequestMapping;
import com.jing.framework.web.mvc.RequestMethod;
import com.jing.framework.web.mvc.RequestParam;
import com.jing.test.service.TeacherService;

/**
 * @author GUO
 * @date 2019/9/24
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @AutoWired
    TeacherService teacherService;

    @RequestMapping(value = "/getTeacher", method = {RequestMethod.GET})
    public String getNum(@RequestParam("teacherName") String teacherName) {
        return teacherService.getTeacher(teacherName);
    }

}
