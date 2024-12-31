package com.lava.utils.autoconfigure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LdbcColumn {
    public String columnName();

}