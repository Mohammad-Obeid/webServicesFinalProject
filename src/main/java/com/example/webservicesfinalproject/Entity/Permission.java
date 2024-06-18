package com.example.webservicesfinalproject.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    Employee_READ("employee:read"),
    Employee_UPDATE("employee:update"),
    Employee_CREATE("employee:create"),
    Employee_DELETE("employee:delete"),
    Customer_READ("customer:read"),
    Customer_UPDATE("customer:update"),
    Customer_CREATE("customer:create"),
    Customer_DELETE("customer:delete"),
;
    @Getter
    private final String permission;
}
