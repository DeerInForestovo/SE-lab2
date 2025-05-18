package com.sismics.docs.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import jakarta.ws.rs.ApplicationPath;

/**
 * JAX-RS application configuration.
 */
@ApplicationPath("/")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        // 注册 REST 包路径（包含所有 resource 类）
        packages("com.sismics.docs.rest.resource");

        // 注册 Jackson JSON 支持
        register(JacksonFeature.class);
    }
}
