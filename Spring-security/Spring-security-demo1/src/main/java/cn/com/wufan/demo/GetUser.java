package cn.com.wufan.demo;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 使用SecurityContextHolder获取用户
 */
public class GetUser {

    /**
     * 从线程中获取用户信息
     * @return
     */
    public String getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }else {
            return principal.toString();
        }
    }

}
