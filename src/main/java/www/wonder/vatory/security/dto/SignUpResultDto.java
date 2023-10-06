package www.wonder.vatory.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 회원가입 결과 정보 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResultDto {

	private boolean success;

	private int code;

	private String msg;

}