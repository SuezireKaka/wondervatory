package www.wonder.vatory.oauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.oauth.model.KakaoAccountVO;
import www.wonder.vatory.oauth.model.OauthToken;
import www.wonder.vatory.oauth.service.OAuthService;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.service.PartyService;
import www.wonder.vatory.security.config.JwtAuthenticationFilter;

@RestController
@RequestMapping("/party")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;
    @Autowired
    private PartyService partyService;

    
    // /party/anonymous/listAllAccount/0000/1
 	@GetMapping("/anonymous/listAllAccount/{ownerId}/{page}")
 	public ResponseEntity<DreamPair<List<AccountVO>, PagingDTO>> listAllAccount(@PathVariable String ownerId, @PathVariable int page) {
 		DreamPair<List<AccountVO>, PagingDTO> result = partyService.listAllAccount(ownerId, page);
 		return new ResponseEntity<>(result, HttpStatus.OK);
 	}

    // 프론트에서 인가코드 돌려 받는 주소
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    @GetMapping("/anonymous/kakaoLogin/{code}")
    public ResponseEntity kakaoLogin(@PathVariable String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = oauthService.getAccessToken(code, "kakao");

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String jwtToken = oauthService.SaveUserAndGetToken(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok().headers(headers).body("success");
    }

    // jwt 토큰으로 유저정보 요청하기
    @GetMapping("kakao/me")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        KakaoAccountVO user = oauthService.getKakaoAccount(request);

        return ResponseEntity.ok().body(user);
    }

}