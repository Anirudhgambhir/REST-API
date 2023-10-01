package taskManagement.service;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import taskManagement.storage.InMemoryTaskManagementRepository;

public class ApplicationModule extends AbstractModule {
    @Provides
    @Singleton
    public TaskManagementRepository getTaskManagementRepository() {
        return new InMemoryTaskManagementRepository();
    }
}
