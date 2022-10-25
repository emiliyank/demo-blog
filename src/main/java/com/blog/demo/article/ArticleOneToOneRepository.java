package com.blog.demo.article;

import org.springframework.data.repository.CrudRepository;

public interface ArticleOneToOneRepository extends CrudRepository<Article, Integer> {
}
