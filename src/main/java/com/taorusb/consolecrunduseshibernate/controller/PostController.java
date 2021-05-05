package com.taorusb.consolecrunduseshibernate.controller;

import com.taorusb.consolecrunduseshibernate.model.Post;
import com.taorusb.consolecrunduseshibernate.model.Writer;
import com.taorusb.consolecrunduseshibernate.service.PostService;
import com.taorusb.consolecrunduseshibernate.service.WriterService;
import org.hibernate.ObjectNotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PostController {

    private PostService postService;
    private WriterService writerService;

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public void setWriterService(WriterService writerService) {
        this.writerService = writerService;
    }

    public List<Post> showByWriterId(ResponseStatus responseStatus, long id) {

        List<Post> posts = new LinkedList<>();
        try {
            posts.addAll(writerService.getById(id).getPosts());
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return posts;
    }

    public Post addNewPost(ResponseStatus responseStatus, long writerId, String content) {

        Post post = null;
        try {
            writerService.getById(writerId);
            postService.savePost(new Post(content, new Writer(writerId)));
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return post;
    }

    public Post updatePost(ResponseStatus responseStatus, long id, String content) {

        Post post = null;
        try {
            post = postService.updatePost(new Post(id, content));
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return post;
    }

    public void deletePost(ResponseStatus responseStatus, long id) {
        try {
            postService.deletePost(id);
            responseStatus.setSuccessful();
        } catch (ObjectNotFoundException e) {
            responseStatus.setElementNotFoundStatus();
        }
    }
}