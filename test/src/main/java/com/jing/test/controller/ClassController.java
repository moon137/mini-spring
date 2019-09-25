package com.jing.test.controller;

import com.jing.framework.beans.AutoWired;
import com.jing.framework.web.mvc.*;
import com.jing.test.service.ClassService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GUO
 * @date 2019/9/24
 */
@Controller
@RequestMapping("/class")
public class ClassController {

    @AutoWired
    ClassService classService;

    @RequestMapping(value = "/getClass" , method = {RequestMethod.GET})
    public String getNum(@RequestParam("className") String className, @RequestParam("count") String count ){
        return classService.getClass(className);
    }

    @ResponseBody
    @RequestMapping(value = "/list" , method = {RequestMethod.POST})
    public List<Map<String ,String>> list(){
        List<Map<String ,String>> list = new ArrayList<>();
        String key = "key";
        String value = "value";
        for (int i = 0; i < 10; i++) {
            Map<String ,String> map = new HashMap<>();
            map.put(key+i,value+i);
            list.add(map);
        }
        return list;
    }
}
