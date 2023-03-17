package com.nhnacademy.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.edu.auth.LoginSuccessHandler;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.GithubLoginFailException;
import com.nhnacademy.edu.repository.resident.ResidentRepository;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubService {
    private final RestTemplate restTemplate;
    private final ResidentRepository residentRepository;
    private final String clientId = "e73300815e1fc50cbfce";
    private final String secret = "28ff9abeb105ac39c3b104176674aab85f03c27c";

    public GithubService(RestTemplate restTemplate, ResidentRepository residentRepository) {
        this.restTemplate = restTemplate;
        this.residentRepository = residentRepository;
    }

    public Map<String, String> getToken(String code) {
        HttpHeaders header = new HttpHeaders();
        header.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", secret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest = new HttpEntity<>(params, header);

        ResponseEntity<String> result = restTemplate.exchange(
                "https://github.com/login/oauth/access_token",
                HttpMethod.POST,
                githubTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> githubToken = null;

        try {
            githubToken = objectMapper.readValue(result.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new GithubLoginFailException("토큰 발행에 실패하였습니다");
        }

        return githubToken;
    }

    public String getUserResource(Map<String, String> githubToken) {
        String accessToken = githubToken.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");

        HttpEntity<String> http = new HttpEntity<>(headers);

        ResponseEntity<String> result = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                http,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> resource = null;

        try {
            resource = objectMapper.readValue(result.getBody(), Map.class);
            System.out.println(resource);
        } catch (JsonProcessingException e) {
            throw new GithubLoginFailException("유저 리소스 불러오는데 실패했습니다.");
        }

        return resource.get("email");
    }

    public Resident userResourceMatchMyDb(String email) {
        Resident resident = residentRepository.findByEmail(email)
                .orElseThrow(() -> new GithubLoginFailException("일치하는 사용자가 없습니다"));

        return resident;
    }

    public UserDetails getUserDetail(Resident resident) {
        return new User(
                resident.getLoginId(),
                resident.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))
        );
    }

    public void makeAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                   Resident resident) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        getUserDetail(resident),
                        "USER_PASSWORD"
                        , Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))
                );

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        LoginSuccessHandler successHandler = new LoginSuccessHandler(null);

        try {
            successHandler.onAuthenticationSuccess(req, resp, authentication);
        } catch (Exception e) {
            throw new GithubLoginFailException("로그인에 실패하였습니다");
        }
    }


}
