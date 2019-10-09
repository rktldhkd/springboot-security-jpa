package board.board.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
 * @NoArgsConstructor : 기본 생성자 자동 추가
		ㄴ예시)@NoArgsConstructor(access = AccessLevel.PROTECTED)
		ㄴaccess = AccessLevel.PROTECTED : 기본생성자의 접근 권한을 protected로 제한
		ㄴ생성자로 protected Posts() {}와 같은 효과
		ㄴEntity 클래스를 프로젝트 코드상에서 기본생성자로 생성하는 것은 막되, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가
 */
@Slf4j
@Entity //jpa 엔티티
@Table(name="jpa_users") //해당 테이블과 매핑
@NoArgsConstructor
@Data //lombok
public class UserEntity {
	@Id //기본키(PK)임을 알림.
	//@GeneratedValue(strategy = GenerationType.AUTO) //기본키 생성 전략. AUTO시, Mysql은 auto_increment / oracle은 기본키에 사용할 시퀀스를 생성한다.
	private String username;
	
	//@Column 으로 컬럼의 속성 지정/초기값 세팅 등이 가능하다.
	@Column(nullable = false) //컬럼 속성 지정. not null.
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private char gender;
	
	@Column(nullable = false)
	private String phone;
	
	//jquery datapicker로 날짜 받으면 string형식으로 받음.
	//db 입력 시, LocalDateTime으로 형변환 후 입력.
	@Column(nullable = false)
	private LocalDate birthday;
	
	@Column(nullable = false)
	private Byte enabled;
	
	@Column(nullable = false)
	private String zipcode;
	
	@Column(nullable = false)
	private String addr;
	
	@Column(nullable = false)
	private String detailAddr;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private LocalDateTime regDate = LocalDateTime.now(); //현재시간
	
	//화면단에서 날짜를 String형으로 받으므로, 여기서 수동으로 setter 추가해야함.
	public void setBirthday(String date) {
		log.debug("string형 날짜 : "+date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate dateTime = LocalDate.parse(date, formatter);
		log.debug("변환 날짜 : "+date);
	    this.birthday = dateTime;
	}//setter birthday
}//class
