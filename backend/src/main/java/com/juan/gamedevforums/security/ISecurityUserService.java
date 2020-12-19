package com.juan.gamedevforums.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
