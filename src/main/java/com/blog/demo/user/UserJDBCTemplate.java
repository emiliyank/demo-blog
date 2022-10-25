package com.blog.demo.user;

import com.blog.demo.blog.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserJDBCTemplate implements UserDAO{
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = jdbcTemplateObject.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        return user;
    }

    @Override
    public void create(String username, String password, boolean enabled) {
        String sql = "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplateObject.update(sql, username, password, enabled);
    }
}
