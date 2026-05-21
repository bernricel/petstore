package com.musngi.petbrowsing.shared;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class RenderDatasourceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String PROPERTY_SOURCE_NAME = "renderDatasourceCompatibility";
    private static final String DATASOURCE_URL_PROPERTY = "spring.datasource.url";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String datasourceUrl = environment.getProperty(DATASOURCE_URL_PROPERTY);

        if (datasourceUrl == null || !datasourceUrl.startsWith("postgresql://")) {
            return;
        }

        Map<String, Object> overrides = new HashMap<>();
        overrides.put(DATASOURCE_URL_PROPERTY, "jdbc:" + datasourceUrl);
        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, overrides));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
