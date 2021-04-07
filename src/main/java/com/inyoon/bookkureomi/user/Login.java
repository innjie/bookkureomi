package com.inyoon.bookkureomi.user;

import lombok.Data;

public @Data class Login {
    private String id;
    private String password;
    private String confirmPassword;
}
