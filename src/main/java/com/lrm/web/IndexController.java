package com.lrm.web;

import com.lrm.NotFoundExceptin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
/**
 * 测试传参以后，进行aop操作 记录日志，
 * 第二个测试则是证明能否跳转到自定义controller拦截
 */
//    @GetMapping("/{id}/{name}")
//    public String index(@PathVariable Integer id,@PathVariable  String name){
////        String blog= null;
////        if(blog==null){
////            throw  new NotFoundExceptin("博客找不到");
////        }
////        int i=9/0;
//        return "index";
//    }



    @GetMapping("/")
    public String index(){
//        String blog= null;
//        if(blog==null){
//            throw  new NotFoundExceptin("博客找不到");
//        }
//        int i=9/0;
        return "index";
    }
}
