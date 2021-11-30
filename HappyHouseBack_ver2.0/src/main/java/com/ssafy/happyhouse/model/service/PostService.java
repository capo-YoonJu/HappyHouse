package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.Post;

public interface PostService {
	
	public boolean createPost(Post post);
	
	public List<Post> retrievePosts();
	
	public Post retrieveDetailPost(int no);
	
	public List<Post> retrievePostsByHashtag(String[] hashtagList);
	
	public boolean updatePost(Post post);
	
	public boolean deletePost(int no);
	
}
