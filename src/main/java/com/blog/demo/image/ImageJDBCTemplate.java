package com.blog.demo.image;

import com.blog.demo.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class ImageJDBCTemplate implements ImageDAO{
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public void create(String filename) {
        String sql = "INSERT INTO images (filename) VALUES (?)";
        jdbcTemplateObject.update(sql, filename);
    }

    @Override
    public Image getImage(Integer id) {
        String sql = "SELECT * FROM images WHERE id = ?";
        Image image = jdbcTemplateObject.queryForObject(sql, new BeanPropertyRowMapper<Image>(Image.class), id);
        return image;
    }

    @Override
    public Integer getLastId() {
        String sql = "SELECT max(id) FROM images";
        Integer maxId = jdbcTemplateObject.queryForObject(sql, Integer.class);
        return maxId;
    }
}
