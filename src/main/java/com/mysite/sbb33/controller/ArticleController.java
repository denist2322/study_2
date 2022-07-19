package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.repository.ArticleRepository;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.Article;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

//    public ArticleController(ArticleRepository articleRepository){
//        this.articleRepository = articleRepository;
//    }
    //C
    @RequestMapping("doWrite")
    @ResponseBody
    public String doWrite(String title, String body){
        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }

        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }

        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        User user = userRepository.findById(1L).get();
        article.setUser(user);

        articleRepository.save(article);

        return "%d번 게시물 생성이 완료되었습니다.".formatted(article.getId());

    }

    //R
    @RequestMapping("list")
    @ResponseBody
    public List<Article> showList(){
        return articleRepository.findAll();
    }

    @RequestMapping("detail")
    @ResponseBody
    public Article showDetail(Long id){
        Article article = articleRepository.findById(id).get();

        return article;
    }
    //U
    @RequestMapping("doModify")
    @ResponseBody
    public String doModify(Long id, String title, String body){
        if(id == null){
            return "게시물번호를 입력해주세요";
        }

        if(Ut.empty(title)){
            return "제목을 입력해주세요.";
        }

        if(Ut.empty(body)){
            return "내용을 입력해주세요.";
        }

        if(!articleRepository.existsById(id)){
            return "게시물이 없습니다.";
        }

        Article article = articleRepository.findById(id).get();

        article.setTitle(title);
        article.setBody(body);
        article.setUpdateDate(LocalDateTime.now());

        articleRepository.save(article);

        return "%d번 게시물 수정이 완료되었습니다.".formatted(article.getId());
    }
    //D
    @RequestMapping("doDelete")
    @ResponseBody
    public String doDelete(Long id){
        if(!articleRepository.existsById(id)){
            return "%d번 게시물은 없습니다.".formatted(id);
        }

//        articleRepository.deleteById(id);
        Article article = articleRepository.findById(id).get();
        articleRepository.delete(article);

        return "%d번 게시물을 삭제했습니다.".formatted(id);
    }
}
