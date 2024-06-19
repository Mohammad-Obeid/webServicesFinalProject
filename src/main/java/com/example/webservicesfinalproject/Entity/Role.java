package com.example.webservicesfinalproject.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.webservicesfinalproject.Entity.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    EMPLOYEE(
            Set.of(
                    EMPLOYEE_READ,
                    EMPLOYEE_UPDATE,
                    EMPLOYEE_DELETE,
                    EMPLOYEE_CREATE,
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    CUSTOMER_CREATE
            )
    ),
    CUSTOMER(
            Set.of(
                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,
                    CUSTOMER_CREATE
            )
    );



    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }

}
