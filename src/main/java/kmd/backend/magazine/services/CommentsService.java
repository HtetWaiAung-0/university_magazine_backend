package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.CommentsRepo;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepo commentsRepo;
}
