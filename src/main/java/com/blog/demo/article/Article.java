package com.blog.demo.article;

import com.blog.demo.image.Image;
import com.blog.demo.image.ImageJDBCTemplate;
import com.blog.demo.image.ImageOneToOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("articles")
public class Article {
    @Autowired
    private ImageOneToOneRepository imageOneToOneRepository;

    @Autowired
    private ImageJDBCTemplate imageJDBCTemplate;

    @Id
    private Integer id;
    private Integer blogId;
    private String title;
    private String content;
    private Integer imageId;

    @MappedCollection(keyColumn = "article_id", idColumn = "article_id")
    private Image image;

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
