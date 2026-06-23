package top.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.news.service.SavedArticleService;

@RestController
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;


}
