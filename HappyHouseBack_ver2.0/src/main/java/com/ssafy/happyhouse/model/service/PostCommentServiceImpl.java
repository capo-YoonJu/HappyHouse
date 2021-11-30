package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.PostCommentDAO;
import com.ssafy.happyhouse.model.dto.PostComment;

@Service
@Transactional
public class PostCommentServiceImpl implements PostCommentService {
	
	@Autowired
	PostCommentDAO postCommentDao;

	@Override
	public PostComment createPostComment(int no, PostComment postComment) {
		postComment.setPost_no(no);
		postCommentDao.insertPostComment(postComment);
		return postCommentDao.selectPostCommentByNo(postComment.getNo());
	}

	@Override
	public List<PostComment> retrievePostComments(int postNo) {
		return postCommentDao.selectPostCommentsByPostNo(postNo);
	}

	@Override
	public boolean updatePostComment(PostComment postComment) {
		return postCommentDao.updatePostComment(postComment);
	}

	@Override
	public boolean deletePostComment(int no) {
		return postCommentDao.deletePostComment(no);
	}

}
