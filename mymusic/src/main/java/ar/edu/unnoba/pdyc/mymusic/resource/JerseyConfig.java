package ar.edu.unnoba.pdyc.mymusic.resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "ar.edu.unnoba.pdyc.mymusic.resource")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(PlayListResource.class);
        register(SongResource.class);
    }
}
