package board.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.repository.JpaBoardRepository;
import board.board.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class JpaBoardServiceImpl implements JpaBoardService{
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;

	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}//selectBoardList

	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		//게시글 작성자 확인
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		log.debug("게시글 저장 - 로그인 유저 id 확인" + username);
//		if(username != "anonymousUser"){ //비로그인 사용자인 경우, principal 값이 anonymouseUser 이다.
//			id	= username;
//		}//end if
		board.setCreatorId(username); //현재 로그인한 사용자 username
		
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}//end if
		jpaBoardRepository.save(board);
	}//saveBoard
	
	////////////
	// JPA의 CrudRepository에서 제공하는 기능으로 id를 가지고 조회.
	// JPA 2.0부터 findById로 변경(원래 findOne). 결과값도 Optional 클래스로 변경.
	////////////
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		
		if(optional.isPresent()) { //객체가 값을 가지고 있는지 확인.
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt() + 1);
			jpaBoardRepository.save(board);
			
			return board;
		}else{
			throw new NullPointerException();
		}//end if-else
	}//selectBoardDetail

	@Override
	public void deleteBoard(int boardIdx) {
		jpaBoardRepository.deleteById(boardIdx);
	}//deleteBoard

	@Override
	public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
		return boardFile;
	}//selectBoardFileInformation
	
}//class
