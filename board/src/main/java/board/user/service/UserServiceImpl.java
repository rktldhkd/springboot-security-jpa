package board.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import board.board.entity.UserAuthorityEntity;
import board.board.entity.UserEntity;
import board.user.repository.UserAuthorityRepository;
import board.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void joinMember(UserEntity user) throws Exception {
		//비번 암호화
		String encoded_pw = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoded_pw);
		user.setEnabled((byte) 1);
		//user테이블 insert
		userRepository.save(user);
		
		//authorities테이블 insert
		UserAuthorityEntity userAuthority = new UserAuthorityEntity();
		userAuthority.setUsername(user.getUsername());
		userAuthority.setAuthority("ROLE_USER");;
		userAuthorityRepository.save(userAuthority);
	}//joinMember

	/* (non-Javadoc)
	 * 아이디 중복 체크
	 * @see goodperson.user.join.service.JoinService#chkUserId(java.lang.String)
	 */
	@Override
	public int duplicateUserId(String userId) throws Exception {
		log.debug("serviceImpl userId : [" + userId + "]");
		int flag = 0;
		
		try {
			UserEntity user = userRepository.findByUsername(userId);
			//log.debug("serviceImple username from db [" + user.getUsername() + "]");
			if(!(user.getUsername().equals(""))) {
				flag++;
			}//end if
			log.debug("flag : " + flag);
			return flag;
		}catch(NullPointerException ne){
			log.debug("no match with username - chkId");
			log.debug("flag : " + flag);
			return flag;
		}//try-catch
	}//duplicateUserId
}//class
