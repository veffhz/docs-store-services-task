package ru.center.inform.docs.client.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }
}
