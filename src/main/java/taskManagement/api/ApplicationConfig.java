package taskManagement.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import taskManagement.exceptions.mapper.InvalidRequestExceptionMapper;
import taskManagement.exceptions.mapper.TaskNotFoundExceptionMapper;
import taskManagement.guice.ApplicationModule;
import taskManagement.guice.MongoDBModule;


import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    @Inject
    public ApplicationConfig(ServiceLocator serviceLocator) {
        register(TaskManagementResource.class);
        register(JsonObjectMapperProvider.class);
        register(InvalidRequestExceptionMapper.class);
        register(TaskNotFoundExceptionMapper.class);

        // bridge the Guice container (Injector) into the HK2 container (ServiceLocator)
        Injector injector = Guice.createInjector(new MongoDBModule(), new ApplicationModule());
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(injector);
    }
}
