package com.jing.test.controller;

import com.jing.framework.beans.AutoWired;
import com.jing.framework.web.mvc.*;
import com.jing.test.service.StudentService;

/**
 * @author GUO
 * @date 2019/9/24
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @AutoWired
    StudentService studentService;

    @RequestMapping(value = "/getStudent" , method = {RequestMethod.GET})
    public String getNum(@RequestParam("studentName") String studentName){
        return studentService.getStudent(studentName);
    }

}
