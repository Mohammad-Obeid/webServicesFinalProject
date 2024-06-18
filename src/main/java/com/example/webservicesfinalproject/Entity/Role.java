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
    Employee(
            Set.of(
                    Employee_READ,
                    Employee_UPDATE,
                    Employee_DELETE,
                    Employee_CREATE,
                    Customer_READ,
                    Customer_UPDATE,
                    Customer_DELETE,
                    Customer_CREATE
            )
    ),
    Customer(Set.of(Customer_READ, Customer_CREATE, Customer_UPDATE, Customer_DELETE)
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
