package com.blog.demo.article;

import com.blog.demo.blog.Blog;

import javax.sql.DataSource;
import java.util.List;

public interface ArticleDAO {
    public void setDataSource(DataSource ds);
    public List<Article> getBlogArticles(Integer blogId);

    public void create(Integer blogId, String title, String content, Integer imageId);

    public Article getArticle(Integer id);

    public void delete(Integer id);

    public void update(Integer id, Article article);
}
