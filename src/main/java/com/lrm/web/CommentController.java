package com.lrm.web;


import com.lrm.po.Comment;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @Autowired
    private BlogService blogService;


    @Value("${comment.avatar}")
    private String avatar;


    /**
     * 当点击博客详情，ajax会自动来访问此页面 根据外键blogid去查询 comment信息并返回到blog页面下的id=commentList去填充内容
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";  //用ajax请求来对这里，并且返回内容为 blog页面下的id=commentList；
    }

    /**
     * 保存评论 ，如果是回复别人 parentID为父亲ID  ，如果自己是父评论 parentID为 -1
     * @param comment
     * @return
     */
    @PostMapping("comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user != null){   //证明是登陆过了  ，博主要添加评论内容 或者是回复别人
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }


}
