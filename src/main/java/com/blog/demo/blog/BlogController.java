package com.blog.demo.blog;

import com.blog.demo.article.Article;
import com.blog.demo.article.ArticleJDBCTemplate;
import com.blog.demo.article.ArticleOneToOneRepository;
import com.blog.demo.image.Image;
import com.blog.demo.image.ImageOneToOneRepository;
import com.blog.demo.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private BlogJDBCTemplate blogJDBCTemplate;

    @Autowired
    private ArticleJDBCTemplate articleJDBCTemplate;

    @Autowired
    private ArticleOneToOneRepository articleOneToOneRepository;

    @Autowired
    private ImageOneToOneRepository imageOneToOneRepository;

    @GetMapping("/blogs")
    public String getAllBlogs(Model model) {
        List<Blog> allBlogs = blogJDBCTemplate.listBlogs();
        model.addAttribute("blogs", allBlogs);

        return "blogs/index";
    }

    @GetMapping("/my-blogs")
    public String getUserBlogs(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = (CustomUserDetail) auth.getPrincipal();
        List<Blog> userBlogs = blogJDBCTemplate.userBlogs(customUserDetail.getUser().getId());
        model.addAttribute("blogs", userBlogs);

        return "blogs/index";
    }

    @GetMapping("/blogs/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "blogs/create";
    }

    @PostMapping("/blogs/store")
    public String storeBlog(Blog blog, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "blogs/create";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail customUserDetail = (CustomUserDetail) auth.getPrincipal();
        Integer userId = customUserDetail.getUser().getId();

        blogJDBCTemplate.create(blog.getTitle(), userId);
        redirectAttributes.addFlashAttribute("message_primary", "Successfully created a new Blog!");
        return "redirect:/my-blogs";
    }

    @GetMapping("blogs/show/{id}")
    public String showSingleBlog(@PathVariable("id") Integer id, Model model) {
        Blog blog = blogJDBCTemplate.getBlog(id);
        List<Article> blogArticles = articleJDBCTemplate.getBlogArticles(id);
        model.addAttribute("blog", blog);
        List<Article> blogArticlesWithImages = this.setImagesToArticles(blogArticles);

        model.addAttribute("articles", blogArticlesWithImages);

        return "blogs/show";
    }

    private List<Article> setImagesToArticles(List<Article> blogArticles) {
        Image image;
        for (Article article: blogArticles) {
            Optional<Image> optionalImage = imageOneToOneRepository.findById(article.getImageId());
            if(!optionalImage.isEmpty()) {
                image = optionalImage.get();
                article.setImage(image);
               }
        }
        return blogArticles;
    }

    @GetMapping("/blogs/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Blog blog = blogJDBCTemplate.getBlog(id);
        model.addAttribute("blog", blog);

        return "blogs/edit";
    }

    @PostMapping("/blogs/update/{id}")
    public String updateBlog(@PathVariable("id") Integer id, @Validated Blog blog, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "blogs/edit";
        }

        blogJDBCTemplate.update(id, blog);
        redirectAttributes.addFlashAttribute("message_success", "Successfully updated a Blog!");
        return "redirect:/my-blogs";
    }

    @PostMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        blogJDBCTemplate.delete(id);
        redirectAttributes.addFlashAttribute("message_danger", "Deleted a Blog!");
        return "redirect:/my-blogs";
    }
}
