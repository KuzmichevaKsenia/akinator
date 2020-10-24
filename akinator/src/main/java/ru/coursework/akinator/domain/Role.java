package ru.coursework.akinator.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, GUEST, PARENT, MEMBER;

    @Override
    public String getAuthority() {
        return name();
    }
}