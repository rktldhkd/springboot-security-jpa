package board.user.repository;

import org.springframework.data.repository.CrudRepository;

import board.board.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String>{

	UserEntity findByUsername(String userId) throws Exception;

}//interface
