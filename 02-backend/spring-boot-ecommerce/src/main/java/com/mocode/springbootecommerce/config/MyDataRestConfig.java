package com.mocode.springbootecommerce.config;

import com.mocode.springbootecommerce.entity.Country;
import com.mocode.springbootecommerce.entity.Product;
import com.mocode.springbootecommerce.entity.ProductCategory;
import com.mocode.springbootecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {




    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntitymanager) {
        entityManager = theEntitymanager;
    }

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        // configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);




        // call an internal helper method
        exposeIds(config);


    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // get entity ids
        // get a list of all entity classes from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create an array of entity types
        List<Class> entityClasses = new ArrayList<>();

        // get the entity types for entities
        for (EntityType entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        // expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
