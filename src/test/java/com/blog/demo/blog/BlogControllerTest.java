package com.blog.demo.blog;

import com.blog.demo.article.Article;
import com.blog.demo.article.ArticleJDBCTemplate;
import com.blog.demo.article.ArticleOneToOneRepository;
import com.blog.demo.image.ImageOneToOneRepository;
import com.blog.demo.user.UserDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BlogController.class)
@WithMockUser
class BlogControllerTest {
    private static Blog BLOG = new Blog();
    private static final String TITLE = "Blog Title 1";

    private static List<Article> BLOG_ARTICLES = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        setBlog();
        setBlogArticlesList();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogJDBCTemplate blogJDBCTemplate;

    @MockBean
    private ArticleJDBCTemplate articleJDBCTemplate;

    @MockBean
    private ArticleOneToOneRepository articleOneToOneRepository;

    @MockBean
    private ImageOneToOneRepository imageOneToOneRepository;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private UserDAO userDAO;

    @Test
    void getAllBlogs() {
    }

    @Test
    void getUserBlogs() {
    }

    @Test
    void showSingleBlog() throws Exception {
        when(blogJDBCTemplate.getBlog(anyInt())).thenReturn(BLOG);
        when(articleJDBCTemplate.getBlogArticles(anyInt())).thenReturn(BLOG_ARTICLES);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://127.0.0.1:8080/blogs/show/1")
                .accept(MediaType.ALL);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Blog Title 1</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/webjars/bootstrap/4.5.0/css/bootstrap.min.css\" />\n" +
                "    <script type=\"text/javascript\" src=\"/webjars/jquery/3.4.1/jquery.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/webjars/bootstrap/4.5.0/js/bootstrap.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h1 class=\"text-center\">Blog: <span>Blog Title 1</span> </h1>\n" +
                "\n" +
                "    <h3 class=\"text-center\">Articles</h3>\n" +
                "    <a href=\"/articles/create/1\" class=\"btn btn-primary\">Add New Article</a>\n" +
                "\n" +
                "    \n" +
                "\n" +
                "        <div class=\"card\">\n" +
                "\n" +
                "            <--img class=\"card-img-top\" th:src=\"@{${article.image.filename}}\" alt=\"Card image cap\"/ -->\n" +
                "            <div class=\"card-body\">\n" +
                "                <h5 class=\"card-title\"><span>Test Title 1</span></h5>\n" +
                "                <p class=\"card-text\"><span>Test Content 1</span></p>\n" +
                "                <a href=\"#\" class=\"btn btn-info\">Read more</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        System.out.println(result.getResponse().getStatus());
        System.out.println(result.getResponse().getContentLength());
        Assert.assertEquals(200, result.getResponse().getStatus());
        //Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    private static void setBlog() {
        BLOG.setId(1);
        BLOG.setTitle(TITLE);
        BLOG.setUserId(1);
    }

    private static void setBlogArticlesList() {
        Article article1 = new Article();
        article1.setId(1);
        article1.setBlogId(1);
        article1.setImageId(1);
        article1.setTitle("Test Title 1");
        article1.setContent("Test Content 1");
        BLOG_ARTICLES.add(article1);
    }
}