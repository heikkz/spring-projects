package com.apress.todo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "todo.authentication")
@Component
public class ToDoProperties {

    private String findByEmailUri;
    private String username;
    private String password;
}
