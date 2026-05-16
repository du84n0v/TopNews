package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.repository.ArticleSectionRepository;

@Service
public class ArticleSectionService {

    @Autowired
    private ArticleSectionRepository articleSectionRepository;
}
