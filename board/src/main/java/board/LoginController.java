package board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public String viewLogin(Model model) {
		// View attribute
		//화면단에 thymeleaf의 {@login_message}에 보낼 값.
		model.addAttribute("login_message", "로그인이 필요합니다.");
		return "login";
	}
	
}//class