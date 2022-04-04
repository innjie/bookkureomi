package com.inyoon.bookkureomi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inyoon.bookkureomi.domain.Recharge;
import com.inyoon.bookkureomi.domain.User;
import com.inyoon.bookkureomi.point.PointMapper;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PointMapper pointMapper;

  /*  @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private boolean enableGroups;
*/
	
	
    public User getUser(int userNo) {
        return userMapper.getUser(userNo);
    }
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }
    public int getUserNo() {
    	return userMapper.getUserNo();
    };
    
    @Transactional
    public void insertUser(User user) {
//        user.setPw(passwordEncoder.encode(user.getPw()));
//        System.out.println(user.getPw());
        userMapper.insertUser(user);
        
        Recharge recharge = new Recharge();
        recharge.setRechargeNo(pointMapper.getRechargeNo(user.getUserNo()));
        recharge.setUser(user);
        recharge.setRcPoint(1000);
        recharge.setTotalPoint(1000);
        recharge.setRcType("recharging");
        recharge.setRcMethod("join");
        pointMapper.rechargePoint(recharge);
        
    }
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
    public void deleteUser(int userNo) {
        userMapper.deleteUser(userNo);
    }

    private String getEncodedPassword(String password) {
        return ("{noop}" + password);
    }
    public void logout(User user) {
        userMapper.logout(user);
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userMapper.getUserById(id);
        Login principal = new Login();
        if(user != null) {
        	principal.setUser(user);
        	
            List<GrantedAuthority> authorities = new ArrayList<>();
        	authorities.add(new SimpleGrantedAuthority(principal.getUserRole()));
            principal.setAuthorities(principal.getAuthorities());
            principal.getUser().setPoint(pointMapper.checkPoint(principal.getUser().getUserNo()));
        }
        
        return principal;
    }


  /*  public void setEnableGroups(boolean enableGroups) {
		this.enableGroups = enableGroups;
	}*/
}
