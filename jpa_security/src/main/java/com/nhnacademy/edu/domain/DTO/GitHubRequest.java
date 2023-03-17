package com.nhnacademy.edu.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GitHubRequest {
    private String clientId;
    private String secretKey;
    private String code;
}
