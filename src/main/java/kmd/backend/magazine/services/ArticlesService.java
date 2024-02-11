package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.ArticlesRepo;

@Service
public class ArticlesService {
    @Autowired
    private ArticlesRepo articlesRepo;
}
