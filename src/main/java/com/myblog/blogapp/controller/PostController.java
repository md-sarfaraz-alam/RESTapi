package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.service.PostService;
import com.myblog.blogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;
//you can also use @Autowired but here constructor is mostly used now
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@Valid  @RequestBody PostDto postDto, BindingResult bindingResult) {
    if(bindingResult.hasErrors()){
        return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

    }

   // @GetMapping

   // public List<PostDto> getAllPosts(){
     // List<PostDto> postDto=postService.getAllPosts();
     // return postDto;
     //2.0 return postService.getAllPosts();
   // }


    // for pagination we have to use @getMapping but i create seperate .
//    @GetMapping
//    public List<PostDto> getAllPosts(
//            @RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
//            @RequestParam(value ="pageSize",defaultValue = "10",required = false) int pageSize)
//    {
//        return postService.getAllPosts(pageNo,pageSize);
//
//    }

     //for Responseback  and sorting
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_Size,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir)
    {
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }

    // http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")long id){
       PostDto dto = postService.getPostByid(id);
        return ResponseEntity.ok(dto);
      // return new ResponseEntity(postService.getPostByid(id),HttpStatus.OK);
        // 2.0 - return ResponseEntity.ok(postService.getPostByid(id));
    }

    //http://localhost:8080/api/posts/1
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
        PostDto dto = postService.updatepost(postDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1
   @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully.",HttpStatus.OK);
   }
}
