package com.myblog.blogapp.service;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);


   // List<PostDto> getAllPosts();



 // for pagination
  // List<PostDto> getAllPosts(int pageNo,int pageSize);

    //for Response back
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);




    PostDto getPostByid(long id);



    PostDto updatepost(PostDto postDto, long id);



    void deletePost(long id);
}
