package com.andrei.sasu.backend.model;

public class JWTTokenResponse {

    private String token;

    public JWTTokenResponse(String accessToken) {
        this.token = accessToken;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

}
