package com.andrei.sasu.backend.model;

public enum Roles {
    USER("ROLE_USER");

    final String name;
    Roles(final String name) {
        this.name = name;
    }
}
