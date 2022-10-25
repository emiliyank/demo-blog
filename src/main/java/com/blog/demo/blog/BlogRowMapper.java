package com.blog.demo.blog;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogRowMapper implements RowMapper {

    @Override
    public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {
        Blog blog = new Blog();
        blog.setId(rs.getInt("id"));
        blog.setTitle(rs.getString("title"));
        blog.setUserId(rs.getInt("user_id"));

        return blog;
    }
}
