package com.blog.demo.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BlogJDBCTemplate implements BlogDAO{
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public void create(String title, Integer userId) {
        String sql = "INSERT INTO blogs(title, user_id) VALUES(?, ?)";
        jdbcTemplateObject.update(sql, title, userId);
    }

    @Override
    public Blog getBlog(Integer id) {
        String sql = "SELECT * FROM blogs WHERE id = ?";
        Blog blog = jdbcTemplateObject.queryForObject(sql, new BeanPropertyRowMapper<Blog>(Blog.class), id);
//        Blog blog = jdbcTemplateObject.queryForObject(
//                sql, new Object[]{id}, new BeanPropertyRowMapper<Blog>()
//        );

        return blog;
    }

    @Override
    public List<Blog> listBlogs() {
        String sql = "SELECT * FROM blogs";
        List<Blog> allBlogs = jdbcTemplateObject.query(sql, new BlogRowMapper());

        return allBlogs;
    }

    @Override
    public List<Blog> userBlogs(Integer userId) {
        String sql = "SELECT * FROM blogs WHERE user_id = ?";
        List<Blog> userBlogs = jdbcTemplateObject.query(sql, new Object[]{userId}, new BlogRowMapper());

        return userBlogs;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM blogs WHERE id = ?";
        jdbcTemplateObject.update(sql, id);
    }

    @Override
    public void update(Integer id, Blog blog) {
        String sql = "UPDATE blogs SET title = ?, user_id = ? WHERE id = ?";
        jdbcTemplateObject.update(sql, blog.getTitle(), blog.getUserId(), blog.getId());
    }
}
