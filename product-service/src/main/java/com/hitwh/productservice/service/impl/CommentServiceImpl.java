package com.hitwh.productservice.service.impl;


import com.hitwh.productservice.entity.Comment;
import com.hitwh.productservice.mapper.CommentMapper;
import com.hitwh.productservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }
}
