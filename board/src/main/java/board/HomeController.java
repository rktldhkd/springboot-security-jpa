package board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import board.board.entity.BoardEntity;
import board.board.service.JpaBoardService;

@Controller
public class HomeController {
	@Autowired
	private JpaBoardService jpaBoardService;
	
	@GetMapping("/")
	public String home(Model model) throws Exception{
		// View attribute
		//index의 {@template}로 전달되는 화면 지정.
		model.addAttribute("template", "board/jpaBoardList");
		
		List<BoardEntity> list = jpaBoardService.selectBoardList();
		model.addAttribute("list", list);
		
		return "index";
	}
}//class
