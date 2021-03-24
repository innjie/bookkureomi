package com.inyoon.bookkureomi.domain;

import lombok.Data;

public @Data class Login {
    private String id;
    private String password;
    private String confirmPassword;
}
