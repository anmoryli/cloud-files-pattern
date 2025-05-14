package com.anmory.cloudfile.obverser;

import com.anmory.cloudfile.mail.JavaxJavaMail;
import com.anmory.cloudfile.model.Users;

import java.util.Set;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-13 下午11:06
 */

public class UsersSubscribed extends Observer{
    private Users user;

    String observerState;

    public UsersSubscribed(String observerState, Users user) {
        this.observerState = observerState;
        this.user = user;
    }

    @Override
    public void update() {
        System.out.println("用户" + user.getUsername() + ",管理员更新了新版本:" + observerState);
        System.out.println(user);
        JavaxJavaMail.sendEmail(Set.of(user.getEmail()), "更新了新版本", observerState);
    }
}
