/**
 * 
 */
package com.java.jwt.jwtauth.payload;

import javax.validation.constraints.NotBlank;

/**
 * @author Mangesh
 * @date 2 Sep 2018
 * @company self
 *
 */
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}