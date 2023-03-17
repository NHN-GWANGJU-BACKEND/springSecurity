package com.nhnacademy.edu.controller.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.edu.entity.Resident;
import com.nhnacademy.edu.exception.GithubLoginFailException;
import com.nhnacademy.edu.service.GithubService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Controller
public class GithubController {
    private final GithubService githubService;
    private final RestTemplate restTemplate;

    public GithubController(GithubService githubService, RestTemplate restTemplate) {
        this.githubService = githubService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/auth/github/callback")
    public String getUserResource(@RequestParam String code,
                                  HttpServletRequest req,
                                  HttpServletResponse resp) {
        if (Objects.isNull(code)) {
            throw new GithubLoginFailException("유저 코드발행에 실패하였습니다");
        }

        Map<String, String> githubToken = githubService.getToken(code);

        String email = githubService.getUserResource(githubToken);

        Resident resident = githubService.userResourceMatchMyDb(email);

        githubService.makeAuthentication(req, resp, resident);

        return "redirect:/";
    }
}
