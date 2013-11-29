package com.yesnault.sag;

import com.yesnault.sag.mail.MailConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

@Configuration
//@PropertySource({"classpath:/META-INF/infrastructure.properties"})
/*@ComponentScan(basePackages = {
        "com.mycompany.myapp.service",
        "com.mycompany.myapp.security"})
*/
@Import(value = {
        //AsyncConfiguration.class,
        CacheConfiguration.class,
        MailConfiguration.class})
//@ImportResource("classpath:META-INF/spring/applicationContext-*.xml")
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Inject
    private Environment env;

    /**
     * Initializes Application.
     * <p/>
     * Spring profiles can be configured with a system property -Dspring.profiles.active=your-active-profile
     * <p/>
     */
    @PostConstruct
    public void initApplication() throws IOException {
        LOGGER.debug("Looking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
            LOGGER.debug("No Spring profile configured, running with default configuration");
        } else {
            for (String profile : env.getActiveProfiles()) {
                LOGGER.debug("Detected Spring profile : {}", profile);
            }
        }
    }
}
