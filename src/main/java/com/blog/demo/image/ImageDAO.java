package com.blog.demo.image;

import com.blog.demo.image.Image;

import javax.sql.DataSource;

public interface ImageDAO {
    public void setDataSource(DataSource ds);

    public void create(String filename);

    public Image getImage(Integer id);

    public Integer getLastId();
}
