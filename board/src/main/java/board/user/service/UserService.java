package board.user.service;

import board.board.entity.UserEntity;

public interface UserService {
	void joinMember(UserEntity user) throws Exception;
	int duplicateUserId(String userId) throws Exception;
}//interface
