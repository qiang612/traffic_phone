package com.rainkaze.traffic.model;

public class JwtAuthResponseDto {
    private String token;
    private UserDto user;

    // Getters and Setters
    public String getToken() { return token; }
    public UserDto getUser() { return user; }
}