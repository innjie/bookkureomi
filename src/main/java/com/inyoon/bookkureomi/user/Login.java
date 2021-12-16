package com.inyoon.bookkureomi.user;

import com.inyoon.bookkureomi.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
@Getter
@Setter
public @Data class Login implements UserDetails, Serializable {
    private String id;
    private String password;
    private String confirmPassword;

    private User user;
    private int point;
    public Login(User user) {
        this.user = user;
    }
    public Login() { //security를 위해서 파라미터가 없는 생성자 필요함

    }

    public int getPoint() {return user.getPoint();}
    public User getUser() {return user;}
    public int getUserNo() {return user.getUserNo();}
    public String getName() {return user.getName();}
    public String getUserRole() {return user.getUserRole();}
    public String getPassword() {return user.getPassword();}


    private boolean isEnabled = true;
    private String username;
    private boolean isCredentialsNonExpired = true;
    private boolean isAccountNonExpired  = true;
    private boolean isAccountNonLocked  = true;

    private Collection<? extends GrantedAuthority> authorities;
}
