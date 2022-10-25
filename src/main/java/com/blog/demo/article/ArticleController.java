package com.blog.demo.article;

import com.blog.demo.image.FileUploadUtil;
import com.blog.demo.image.ImageJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Controller
public class ArticleController {
    @Autowired
    private ArticleJDBCTemplate articleJDBCTemplate;

    @Autowired
    private ImageJDBCTemplate imageJDBCTemplate;

    @GetMapping("/articles/create/{blogId}")
    public String showCreateForm(@PathVariable("blogId") Integer blogId, Model model) {
        Article article = new Article();
        article.setBlogId(blogId);
        model.addAttribute("article", article);

        return "articles/create";
    }

    @PostMapping("/articles/store")
    public String store(Article article, @RequestParam("image") MultipartFile multipartFile, BindingResult result, RedirectAttributes redirectAttributes) throws IOException {
        if(result.hasErrors()) {
            return "articles/create";
        }

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String uploadDir = "article-images/" + uuid;
        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        String filePath = uploadDir + "/" + filename;
        imageJDBCTemplate.create(filePath);

        Integer lastImageId = imageJDBCTemplate.getLastId();
        articleJDBCTemplate.create(article.getBlogId(), article.getTitle(), article.getContent(), lastImageId);

        return "redirect:/blogs/show/" + article.getBlogId();
    }
}
