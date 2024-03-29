package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.service.JpaBoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class JpaBoardController {
	
	@Autowired
	private JpaBoardService jpaBoardService;
	
	@RequestMapping(value="/jpa/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardList");
		
		List<BoardEntity> list = jpaBoardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	//get방식일때 write 화면데이터.
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/jpaBoardWrite";
	}
	
	//post방식일때 write 화면데이터.
	//JPA save는 insert/update 역할 둘 다 수행
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.POST)
	public String writeBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		jpaBoardService.saveBoard(board, multipartHttpServletRequest);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		
		BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		//로그인 사용자 id 가져오기
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		log.debug("controller - detail() - username값 : " + username);
		mv.addObject("username", username);
		
		return mv;
	}
	
	//JPA save는 insert/update 역할 둘 다 수행
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardEntity board) throws Exception{
		jpaBoardService.saveBoard(board, null);
		return "redirect:/jpa/board/"+board.getBoardIdx();
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		jpaBoardService.deleteBoard(boardIdx);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(int boardIdx, int idx, HttpServletResponse response) throws Exception{
		BoardFileEntity file = jpaBoardService.selectBoardFileInformation(boardIdx, idx);
		
		byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));
		
		String fileName = file.getOriginalFileName();
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(files);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
