package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.repository.SavedArticleRepository;

@Service
public class SavedArticleService {

    @Autowired
    private SavedArticleRepository savedArticleRepository;
}
