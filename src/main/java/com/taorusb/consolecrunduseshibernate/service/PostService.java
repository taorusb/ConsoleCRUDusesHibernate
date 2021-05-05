package com.taorusb.consolecrunduseshibernate.service;

import com.taorusb.consolecrunduseshibernate.model.Post;

public interface PostService {

    Post getById(Long id);

    Post updatePost(Post post);

    Post savePost(Post post);

    void deletePost(Long id);

}
