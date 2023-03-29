package ro.msg.learning.shop.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractPostgreSQLContainer extends PostgreSQLContainer<AbstractPostgreSQLContainer> {

    private static final String IMAGE_VERSION = "postgres:11.1";
    private static AbstractPostgreSQLContainer container;

    public AbstractPostgreSQLContainer(){
        super(IMAGE_VERSION);
    }

    public static AbstractPostgreSQLContainer getInstance(){
        if(container == null){
            container = new AbstractPostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start(){
        super.start();
        System.setProperty("DB_URL",container.getJdbcUrl());
        System.setProperty("DB_USERNAME",container.getUsername());
        System.setProperty("DB_PASSWORD",container.getPassword());
    }

    @Override
    public void stop(){
        //JVM handles
    }
}
