package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.PostDAO;
import com.ssafy.happyhouse.model.dto.Hashtag;
import com.ssafy.happyhouse.model.dto.Post;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDao;
	
	public boolean createHashtag(int post_no, List<String> tags) {
		HashMap<String, Integer> postHashtag = new HashMap<String, Integer>();
		postHashtag.put("post_no", post_no);
		
		Hashtag hashtag = new Hashtag();
		for (String tag : tags) {
			hashtag.setTag(tag);
			
			Integer hashtag_no = postDao.selectHashtag(tag);
			if (hashtag_no == null) {
				postDao.insertHashtag(hashtag);
				hashtag_no = hashtag.getNo();
			}
			
			postHashtag.put("hashtag_no", hashtag_no);
			postDao.insertPostHashtagMapping(postHashtag);
		}
		return true;
	}
	
	@Override
	public boolean createPost(Post post) {
		String content = post.getContent().replace("\r\n","<br>");
		post.setContent(content);
		postDao.insertPost(post);
		return createHashtag(post.getNo(), post.getTags());
	}

	@Override
	public List<Post> retrievePosts() {
		return postDao.selectPosts();
	}

	@Override
	public Post retrieveDetailPost(int no) {
		return postDao.selectPostByNo(no);
	}

	@Override
	public List<Post> retrievePostsByHashtag(String[] hashtagList) {
		return postDao.selectPostsByHashtag(hashtagList);
	}

	@Override
	public boolean updatePost(Post post) {
		int post_no = post.getNo();
		postDao.updatePost(post);
		postDao.deletePostHashtagMapping(post_no);
		return createHashtag(post_no, post.getTags());
	}

	@Override
	public boolean deletePost(int no) {
		return postDao.deletePost(no);
	}

}
