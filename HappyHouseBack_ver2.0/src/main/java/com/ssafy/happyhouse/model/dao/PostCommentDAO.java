package com.ssafy.happyhouse.model.dao;

import java.util.List;

import com.ssafy.happyhouse.model.dto.PostComment;


public interface PostCommentDAO {
	
	public boolean insertPostComment(PostComment postComment);
	
	public List<PostComment> selectPostCommentsByPostNo(int postNo);
	
	public PostComment selectPostCommentByNo(int no);
	
	public boolean updatePostComment(PostComment postComment);
	
	public boolean deletePostComment(int no);
	
}
