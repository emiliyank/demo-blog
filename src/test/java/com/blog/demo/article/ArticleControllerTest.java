package com.blog.demo.article;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArticleControllerTest {
    private static Article ARTICLE;
    private static final String TITLE = "Test Title 1";
    private static final String CONTENT = "Article 1 test content";

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleJDBCTemplate articleJDBCTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        setArticle();
    }

    @Test
    void givenValidTitleAndContentThenStoreArticle() {
        //articleController.store(ARTICLE, )
        //articleJDBCTemplate.create(1, TITLE, CONTENT, 1);
        //verify(articleJDBCTemplate, times(1)).create(ARTICLE);
    }

    private void setArticle() {
        ARTICLE.setId(1);
        ARTICLE.setTitle(TITLE);
        ARTICLE.setBlogId(1);
        ARTICLE.setContent(CONTENT);
    }
}