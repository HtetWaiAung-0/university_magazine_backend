package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.repos.CommentRepo;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
}
