package com.company.project.model;

import org.springframework.stereotype.Component;


@Component
public class HostHolder {

    //使用全局的线程都可以看到
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
