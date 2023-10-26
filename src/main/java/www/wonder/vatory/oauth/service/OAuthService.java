package www.wonder.vatory.oauth.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import www.wonder.vatory.oauth.mapper.KakaoMapper;
import www.wonder.vatory.oauth.model.KakaoAccountVO;
import www.wonder.vatory.oauth.model.KakaoProfile;
import www.wonder.vatory.oauth.model.OauthToken;
import www.wonder.vatory.party.model.PersonVO;
import www.wonder.vatory.party.service.PartyService;
import www.wonder.vatory.security.config.JwtTokenProvider;
import www.wonder.vatory.security.model.SignInResultDto;

@Service
public class OAuthService {

    //환경 변수 가져오기
	@Value("#{'${kakao.client.id}'}")
    String client_id;

	@Value("#{'${kakao.client.secret}'}")
    String client_secret;
    
	@Value("#{'${kakao.redirect.url}'}")
    String redirect_url;
	
	@Autowired
	KakaoMapper kakaoMapper;
	@Autowired
	PartyService partyService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

    public OauthToken getAccessToken(String code, String loginType) {
    	OauthToken oauthToken = null;
    	
    	// POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();
        
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
    	
    	switch (loginType) {
    	case "kakao" :
    		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    		
    		 // HttpBody 오브젝트 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", client_id);
            params.add("redirect_uri", redirect_url + "/party/callback");
            params.add("code", code);
            params.add("client_secret", client_secret);
            
            // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                    new HttpEntity<>(params, headers);

            // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
            ResponseEntity<String> accessTokenResponse = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

            // JSON 응답을 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            
            try {
                oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    	}
        return oauthToken;
    }

    public KakaoProfile findProfile(String token) {
        // POST 방식으로 key=value 데이터 요청
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 정보를 하나의 오브젝트에 담음
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // Http 요청 (POST 방식) 후, response 변수의 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // JSON 응답을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public KakaoAccountVO getKakaoAccount(HttpServletRequest request) {
        Long userCode = (Long) request.getAttribute("userCode");

        KakaoAccountVO user = kakaoMapper.findByUserCode(userCode);

        return user;
    }

    public String SaveUserAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);
        
        KakaoAccountVO kAccount = kakaoMapper.findByKakaoId(profile.getId());
        PersonVO kResponse = new PersonVO();
        
        
        if(kAccount == null) {
        	kAccount = KakaoAccountVO.builder()
                    .kakaoId(profile.getId())
                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .kakaoNick(profile.getKakao_account().getProfile().getNickname())
                    .build();

            kakaoMapper.createKakaoAccount(kAccount);
            partyService.createPerson(kResponse);
        }

        return createToken(kAccount);
    }

    public String createToken(KakaoAccountVO kAccount) {
        // Jwt 생성 후 헤더에 추가해서 보내줌
        String jwtToken = jwtTokenProvider.createToken(
				String.valueOf(String.valueOf(kAccount.getKakaoId())),
				kAccount.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()));

        return jwtToken;
    }


}