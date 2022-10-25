package com.blog.demo.user;

import javax.sql.DataSource;

public interface UserDAO {
    public void setDataSource(DataSource ds);

    public User findUserByUsername(String username);

    public void create(String username, String password, boolean enabled);
}
