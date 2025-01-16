package com.easychat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${ws.port}")
    private Integer wsPort;

    @Value("${project.folder}")
    private String projectFolder;

    @Value("${admin.emails}")
    private String adminEmails;

    public Integer getWsPort() {
        return wsPort;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getAdminEmails() {
        return adminEmails;
    }
}
