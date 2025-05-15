package com.anmory.cloudfile.controller;

import com.anmory.cloudfile.adapter.Adapter;
import com.anmory.cloudfile.adapter.ConcreteAliPay;
import com.anmory.cloudfile.adapter.ConcreteWeChatPay;
import com.anmory.cloudfile.cipher.AdvancedCipher;
import com.anmory.cloudfile.cipher.Cipher;
import com.anmory.cloudfile.cipher.ComplexCipher;
import com.anmory.cloudfile.cipher.SimpleCipher;
import com.anmory.cloudfile.model.Users;
import com.anmory.cloudfile.obverser.UsersSubscribed;
import com.anmory.cloudfile.obverser.VersionUpdate;
import com.anmory.cloudfile.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午10:07
 */

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService usersService;
/////////////单例模式实例/////////////////////////////////
    @Autowired
    UsersController(UsersService usersService) {
        this.usersService = usersService;
        UsersService.getInstance();
    }
/////////////单例模式实例/////////////////////////////////

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Users login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       HttpServletResponse response,
                       @RequestParam boolean rememberMe) { // 增加 rememberMe 参数
        password = getEncrypt(password);
        Users user = usersService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("session_user_key", user);

            if (rememberMe) {
                String token = UUID.randomUUID().toString(); // 生成唯一 Token
                usersService.updateToken(token, user.getId()); // 存储到数据库

                Cookie cookie = new Cookie("remember_me", token);
                cookie.setMaxAge(60 * 60 * 24 * 10000); // 保留10000天
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setSecure(true); // 生产环境建议启用 HTTPS
                response.addCookie(cookie);
            }

            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setMaxAge(rememberMe ? 60 * 60 * 24 * 30 : -1); // 永久或跟随 rememberMe
            sessionCookie.setPath("/");
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true);
            response.addCookie(sessionCookie);

            log.info("登录成功，用户: {}", username);
            return user;
        }
        log.info("用户名或密码错误");
        return null;
    }

////////////////////观察者模式实例//////////////////////////////////////
    @RequestMapping("/update")
    public void update(String content) {
        System.out.println("update:"+content);
        List<Users> users = usersService.selectAll();
        VersionUpdate versionUpdate = new VersionUpdate();
        for(Users user : users) {
            versionUpdate.addObserver(new UsersSubscribed(content, user));
        }
        // 设置subject状态
        versionUpdate.setSubjectState(content);
        versionUpdate.notifyObservers();
    }
////////////////////观察者模式实例//////////////////////////////////////

    @RequestMapping("/register")
    public int register(String username, String password, String email, HttpSession session) {
        System.out.println(password);
        Users user = usersService.findByUsername(username);
        if (user != null) {
            log.info("用户名已存在");
            return user.getId();
        }
        password = getEncrypt(password);
        int ret = usersService.insert(username, password, email);
        Users userRegister = usersService.findByUsername(username);
        session.setAttribute("session_user_key", userRegister);
        return ret;
    }
///////////////////适配器模式实例/////////////////////////////////////////////////
    @PostMapping("/expandStorage")
    public String expandStorage(int type) {
        Adapter adapter = new Adapter();
        adapter.setAliPay(new ConcreteAliPay());
        adapter.setWeChatPay(new ConcreteWeChatPay());

        if(type == 1) {
            return adapter.aliPay();
        }
        return adapter.weChatPay();
    }
////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session, HttpServletResponse response) {
        session.invalidate(); // 清除 session_user_key
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

/////////////////////装饰模式实例//////////////////////////////////////////
    public String getEncrypt(String password) {
        Cipher cipher = new SimpleCipher();
        Cipher complexCipher = new ComplexCipher(cipher);
        Cipher advancedCipher = new AdvancedCipher(complexCipher);

        return advancedCipher.encrypt(password);
    }
/////////////////////装饰模式实例//////////////////////////////////////////

    @RequestMapping("/getCurrentUser")
    public Users getCurrentUser(HttpSession session) {
        return (Users) session.getAttribute("session_user_key");
    }
}
