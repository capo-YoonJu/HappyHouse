package com.ssafy.happyhouse.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.happyhouse.model.dto.Hashtag;
import com.ssafy.happyhouse.model.dto.Post;

public interface PostDAO {
	
	public Integer insertHashtag(Hashtag tag);
	
	public Integer insertPost(Post post);
	
	public boolean insertPostHashtagMapping(HashMap<String, Integer> postHashtag);
	
	public Integer selectHashtag(String tag);
	
	public List<Post> selectPosts();
	
	public Post selectPostByNo(int no);
	
	public List<Post> selectPostsByHashtag(String[] hashtagList);
	
	public boolean updatePost(Post post);
	
	public boolean deletePostHashtagMapping(int post_no);
	
	public boolean deletePost(int no);
}
