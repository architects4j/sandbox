package com.architects4j.workshop.microstream.helidon.product;







import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/")
@ApplicationScoped
public class RestaurantRestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HelloController.class);
        classes.add(ProductResource.class);
        return classes;
    }
}
