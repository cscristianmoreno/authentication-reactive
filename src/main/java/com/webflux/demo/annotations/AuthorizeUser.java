package com.webflux.demo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAUthorit('ROLE_READ')")
public @interface AuthorizeUser {
    
}
