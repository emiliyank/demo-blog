package com.blog.demo.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class ArticleJDBCTemplate implements ArticleDAO{
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public List<Article> getBlogArticles(Integer blogId) {
        String sql = "SELECT * FROM articles WHERE blog_id = ?";
        List<Article> blogArticles = jdbcTemplateObject.query(sql, new Object[]{blogId}, new BeanPropertyRowMapper(Article.class));

        return blogArticles;
    }

    @Override
    public void create(Integer blogId, String title, String content, Integer imageId) {
        String sql = "INSERT INTO articles (blog_id, title, content, image_id) VALUES(?, ?, ?, ?)";
        jdbcTemplateObject.update(sql, blogId, title, content, imageId);
    }

    @Override
    public Article getArticle(Integer id) {
        String sql = "SELECT * FROM articles WHERE id = ?";
        Article article = jdbcTemplateObject.queryForObject(sql, new BeanPropertyRowMapper<Article>(Article.class), id);

        return article;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        jdbcTemplateObject.update(sql, id);
    }

    @Override
    public void update(Integer id, Article article) {
        String sql = "UPDATE articles SET title = ?, content = ?, image_id = ?";
        jdbcTemplateObject.update(sql, article.getTitle(), article.getContent(), article.getImageId());
    }
}
