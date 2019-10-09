package board.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import board.board.entity.UserEntity;
import board.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j //log 객체 자동 생성
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/openAgreement")
	public ModelAndView openAgreement() throws Exception{
		ModelAndView mv = new ModelAndView("user/AgreementTerms");
		return mv;
	}//openAgreement
	
	
	@GetMapping("/jpa/user/openMemberInput")
	public ModelAndView openMemberInput() throws Exception{
		ModelAndView mv = new ModelAndView("user/memInfo_input");
		return mv;
	}//openMemberInput - member data input template
	
	@Transactional
	@PostMapping("/jpa/user/openMemberInput")
	public String joinMember(UserEntity user) throws Exception{
		log.debug("사용자가입 시작");
		log.debug("user entity 값 : [ " + user + " ]");
		userService.joinMember(user);
		log.debug("사용자가입 끝");
		return "redirect:/jpa/board";
	}//joinMember - member data transfort to DB
	
	
	/**
	 * 아이디 중복 확인. 
	 * ajax, json 사용 시, @ResponseBody 애노테이션 사용 요망. Unknown return value type [java.lang.Integer] 등의 에러 발생방지.
	 * @return The number of duplicated ID
	 */
	@PostMapping(value="/join/chkId")
	public @ResponseBody int chkId(@RequestParam String userId) throws Exception{
		log.debug("Controller chkId userId : [" + userId +"]");
		int result  = userService.duplicateUserId(userId);
		log.debug("the number of duplicating Id : [" + result + "]");
		return result;
	}//chkId
	
}//class
