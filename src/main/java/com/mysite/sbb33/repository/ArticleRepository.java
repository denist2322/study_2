package com.mysite.sbb33.repository;

import com.mysite.sbb33.vo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
