package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.PostComment;

public interface PostCommentService {

	public PostComment createPostComment(int no, PostComment postComment);
	
	public List<PostComment> retrievePostComments(int postNo);
	
	public boolean updatePostComment(PostComment postComment);
	
	public boolean deletePostComment(int no);
}
