package com.blog.demo.blog;

import javax.sql.DataSource;
import java.util.List;

public interface BlogDAO {
    public void setDataSource(DataSource ds);

    public void create(String title, Integer userId);

    public Blog getBlog(Integer id);

    public List<Blog> listBlogs();

    public List<Blog> userBlogs(Integer userId);

    public void delete(Integer id);

    public void update(Integer id, Blog blog);
}
