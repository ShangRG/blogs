package com.lrm.web.admin;

import com.lrm.po.*;
import com.lrm.service.BlogService;
import com.lrm.service.TagService;
import com.lrm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/15.
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,Model model,BlogQuery blog){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typeService.listType());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){

        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    private List<Object> convertToList(String ids) {  //id=“1，2，3”  切割字符串   1  2   3
        List<Object> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            Object[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(idarray[i]);
            }
        }
        return list;
    }


    @PostMapping("/blogs")     //新增和修改将共用这一个方法
    public String post(Blog blog,RedirectAttributes attributes, HttpSession session){

        //一下注释代码用来存储标签
//        List<Type> types = typeService.listType();
//        List<Object> objects = convertToList(blog.getTagIds());
//        Map<Object, Object> map =new HashMap<>();
//        List<Object> list=new ArrayList<>();
//        for(int i=0;i<types.size();i++){
//            String a=types.get(i).getId()+"";
//            map.put(a,a);
//            System.out.println(a);
//        }
//        for (Object o:objects){
//            Object c=map.get(o);
//            if(c==null){
//                list.add(o);
//                System.out.println(o);
//            }
//        }
//        if(list.size()>0){
//            //找到新加标签，进行保存标签
//            for (int i=0;i<list.size();i++){
//                String name= (String) list.get(i);
//                tagService.saveTag(name);
//            }
//        }

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId()==null){  //新增
            b= blogService.saveBlog(blog);
        }else{
            b= blogService.updateBlog(blog.getId(),blog);  //修改
        }
        if(b==null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model,@PathVariable Long id){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types", typeService.listType());
        Blog blog = blogService.getBlog(id);
        blog.init();  //将查询道的blog对象里的ids  处理成  1，2，3  为了在前端页面显示
        model.addAttribute("blog", blog);
        return "/admin/blogs-input";

    }

    //@{/admin/blogs/{id}/delete(id=${blog.id})
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

}
