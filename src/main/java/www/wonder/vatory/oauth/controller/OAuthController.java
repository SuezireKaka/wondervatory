package www.wonder.vatory.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import www.wonder.vatory.oauth.model.KakaoAccountVO;
import www.wonder.vatory.oauth.model.OauthToken;
import www.wonder.vatory.oauth.service.OAuthService;
import www.wonder.vatory.party.service.PartyService;
import www.wonder.vatory.security.model.SignInResultDto;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;
    @Autowired
    private PartyService partyService;


    // 프론트에서 인가코드 돌려 받는 주소
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    // /oauth/anonymous/kakao/login/ewioajieoawrejew
    @GetMapping("/anonymous/kakao/login/{code}")
    public ResponseEntity<SignInResultDto> kakaoLogin(@PathVariable String code) {

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = oauthService.getAccessToken(code, "kakao");

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        SignInResultDto result = oauthService.SaveUserAndLogin(oauthToken.getAccess_token());

        return ResponseEntity.ok(result);
    }

    // jwt 토큰으로 유저정보 요청하기
    @GetMapping("/kakao/me")
    public ResponseEntity<KakaoAccountVO> getCurrentUser(HttpServletRequest request) {

        KakaoAccountVO user = oauthService.getKakaoAccount(request);

        return ResponseEntity.ok().body(user);
    }

}