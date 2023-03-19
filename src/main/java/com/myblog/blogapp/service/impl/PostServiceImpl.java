package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // helps to convert dto object to entity

//        Post post= new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post post= mapToEntity(postDto);

        Post postEntity = postRepo.save(post);

        PostDto dto= mapToDto(postEntity);


        //convert entity to dto

//        PostDto dto=new PostDto();
//        dto.setId(postEntity.getId());
//        dto.setTitle(postEntity.getTitle());
//        dto.setDescription(postEntity.getDescription());
//        dto.setContent(postEntity.getContent());
        return dto;
    }

//     pagination getmapping calling
  //  @Override
//    public List<PostDto> getAllPosts(int pageNo, int pageSize) {
//        Pageable pageable= PageRequest.of(pageNo,pageSize);
//        Page<Post> posts = postRepo.findAll(pageable);
//        List<Post> content = posts.getContent();
       // return content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());


        // for response back of pagination like total pages and all
        @Override
        public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
 //       1.0 using if
//        Sort sort=null;
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort=Sort.by(sortBy).ascending();
//        }else {
//            sort=Sort.by(sortBy).descending();
//
//        }
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.
                    by(sortBy).descending();

            Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
            Page<Post> posts = postRepo.findAll(pageable);
            List<Post> content = posts.getContent();
        List<PostDto> contents = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

   // @Override   for normal getting posts from db
   // public List<PostDto> getAllPosts() {
       // List<Post> posts=postRepo.findAll();
      // return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
       //2.0 return postDtos;
  //  }

    @Override
    public PostDto getPostByid(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        //   Optional<Post> byId =
      //  Post post = byId.get();
       PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatepost(PostDto postDto,long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepo.save(post);
        return mapToDto(newPost);
    }

    @Override
    public void deletePost(long id) {
        postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        postRepo.deleteById(id);
    }

    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post= new Post();
//       post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
       return post;
    }
    public PostDto mapToDto(Post post){

        PostDto dto = mapper.map(post, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }
}
