package com.yesnault.sag.configuration;

import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.yesnault.sag.ApplicationConfiguration;
import com.yesnault.sag.metrics.MetricsConfiguration;
import net.sf.ehcache.constructs.web.filter.GzipFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

@ComponentScan
//@EnableAutoConfiguration
public class WebConfiguration implements ServletContextListener {

    /*    public static void main(String[] args)  {
            ApplicationContext rootContext = SpringApplication.run(WebConfiguration.class, args);
            AbstractApplicationContext context = new AnnotationConfigApplicationContext(PersistenceApplication.class);

        }
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        LOGGER.info("Web application configuration");

        LOGGER.debug("Configuring Spring root application context");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfiguration.class);
        rootContext.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);

        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

        initSpring(servletContext, rootContext);
        initSpringSecurity(servletContext, disps);
        initMetrics(servletContext, disps);
        initGzip(servletContext, disps);

        LOGGER.debug("Web application fully configured");
    }

    /**
     * Initializes the GZip filter.
     */
    private void initGzip(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        LOGGER.debug("Registering GZip Filter");
        FilterRegistration.Dynamic gzipFilter = servletContext.addFilter("gzipFilter",
                new GzipFilter());

        gzipFilter.addMappingForUrlPatterns(disps, true, "/rest/*");
        gzipFilter.addMappingForUrlPatterns(disps, true, "/js/*");
        gzipFilter.addMappingForUrlPatterns(disps, true, "/css/*");
        gzipFilter.addMappingForUrlPatterns(disps, true, "*.html");
        gzipFilter.setAsyncSupported(true);
    }

    /**
     * Initializes Spring and Spring MVC.
     */
    private ServletRegistration.Dynamic initSpring(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        LOGGER.debug("Configuring Spring Web application context");
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(rootContext);
        dispatcherServletConfiguration.register(DispatcherServletConfiguration.class);

        LOGGER.debug("Registering Spring MVC Servlet");
        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(
                dispatcherServletConfiguration));
        dispatcherServlet.addMapping("/rest/*");
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.setAsyncSupported(true);
        return dispatcherServlet;
    }

    /**
     * Initializes Spring Security.
     */
    private void initSpringSecurity(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        LOGGER.debug("Registering Spring Security Filter");
        /* TODO FilterRegistration.Dynamic springSecurityFilter = servletContext.addFilter("springSecurityFilterChain",
                new DelegatingFilterProxy());

        springSecurityFilter.setAsyncSupported(true);
        springSecurityFilter.addMappingForUrlPatterns(disps, false, "/*");
        springSecurityFilter.setAsyncSupported(true);
        */
    }

    /**
     * Initializes Metrics.
     */
    private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        LOGGER.debug("Initializing Metrics registries");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE,
                MetricsConfiguration.METRIC_REGISTRY);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,
                MetricsConfiguration.METRIC_REGISTRY);
        servletContext.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY,
                MetricsConfiguration.HEALTH_CHECK_REGISTRY);

        LOGGER.debug("Registering Metrics Filter");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter",
                new InstrumentedFilter());

        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");

        LOGGER.debug("Registering Metrics Admin Servlet");
        ServletRegistration.Dynamic metricsAdminServlet =
                servletContext.addServlet("metricsAdminServlet", new AdminServlet());

        metricsAdminServlet.addMapping("/metrics/*");
        metricsAdminServlet.setLoadOnStartup(2);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Destroying Web application");
        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        AnnotationConfigWebApplicationContext gwac = (AnnotationConfigWebApplicationContext) ac;
        gwac.close();
        LOGGER.debug("Web application destroyed");
    }

}