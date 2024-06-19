package com.example.webservicesfinalproject.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_UPDATE("employee:update"),
    EMPLOYEE_CREATE("employee:create"),
    EMPLOYEE_DELETE("employee:delete"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),
;
    @Getter
    private final String permission;
}


