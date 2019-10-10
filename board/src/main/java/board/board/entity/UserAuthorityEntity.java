package board.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //jpa 엔티티
@Table(name="jpa_authorities")
@NoArgsConstructor
@Data //lombok
public class UserAuthorityEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String authority;
}//class
