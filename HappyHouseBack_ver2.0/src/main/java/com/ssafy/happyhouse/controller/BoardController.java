package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.Post;
import com.ssafy.happyhouse.model.dto.PostComment;
import com.ssafy.happyhouse.model.service.PostCommentService;
import com.ssafy.happyhouse.model.service.PostService;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private PostCommentService postCommentService;
	
	@GetMapping
	public ResponseEntity<List<Post>> retrievePosts() {
		List<Post> posts = postService.retrievePosts();
		if (posts.size()!=0) return ResponseEntity.ok(posts);
		else return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<Post> retrievePostByNo(@PathVariable int no) {
		Post post = postService.retrieveDetailPost(no);
		if (post != null) return ResponseEntity.ok(post);
		else return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/hashtags")
	public ResponseEntity<List<Post>> retrievePostByHashtag(@RequestParam(value="tag[]") String[] hashtagList) {
		List<Post> posts = postService.retrievePostsByHashtag(hashtagList);
		if (posts.size()!=0) return ResponseEntity.ok(posts);
		else return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{no}/comments")
	public ResponseEntity<List<PostComment>> retrievePostComments(@PathVariable int no) {
		List<PostComment> postComments = postCommentService.retrievePostComments(no);
		if (postComments.size()!=0) return ResponseEntity.ok(postComments);
		else return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Boolean> registerPost(@RequestBody Post post) {
		return ResponseEntity.ok(postService.createPost(post));
	}
	
	@PostMapping("/{no}/comment")
	public ResponseEntity<PostComment> registerPostComment(@PathVariable int no, @RequestBody PostComment postComment) {
		return ResponseEntity.ok(postCommentService.createPostComment(no, postComment));
	}
	
	@PutMapping("/{no}")
	public ResponseEntity<Boolean> updatePost(@RequestBody Post post) {
		return ResponseEntity.ok(postService.updatePost(post));
	}
	
	@PutMapping("/{no}/comment")
	public ResponseEntity<Boolean> updatePostComment(@RequestBody PostComment postComment) {
		return ResponseEntity.ok(postCommentService.updatePostComment(postComment));
	}
	
	@DeleteMapping("/{no}")
	public ResponseEntity<Boolean> deletePost(@PathVariable int no) {
		return ResponseEntity.ok(postService.deletePost(no));
	}
	
	@DeleteMapping("/{no}/comment")
	public ResponseEntity<Boolean> deletePostComment(@PathVariable int no) {
		return ResponseEntity.ok(postCommentService.deletePostComment(no));
	}
	 
}
