package com.webflux.demo.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ROLE_SAVE')")
public @interface AuthorizeAdmin {
    
}
