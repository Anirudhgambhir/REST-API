package taskManagement.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import taskManagement.helper.MongoDBTask;
import taskManagement.service.TaskManagementRepository;
import taskManagement.storage.MongoDBTaskManagementRepository;

public class ApplicationModule extends AbstractModule {
    @Provides
    @Singleton
    public TaskManagementRepository getTaskManagementRepository(MongoCollection<MongoDBTask> mongoCollection) {
        return new MongoDBTaskManagementRepository(mongoCollection);
    }
}
