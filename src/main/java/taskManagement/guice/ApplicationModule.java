package taskManagement.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.client.MongoCollection;
import taskManagement.helper.MongoDBTask;
import taskManagement.service.TaskManagementRepository;
import taskManagement.service.TaskUpdate;
import taskManagement.storage.MongoDBTaskManagementRepository;
import taskManagement.validator.Validator;
import taskManagement.validator.impl.ObjectValidatorImpl;
import taskManagement.validator.impl.StringValidatorImpl;
import taskManagement.validator.impl.TaskUpdateValidatorImpl;

public class ApplicationModule extends AbstractModule {
    @Provides
    @Singleton
    public TaskManagementRepository getTaskManagementRepository(MongoCollection<MongoDBTask> mongoCollection) {
        return new MongoDBTaskManagementRepository(mongoCollection);
    }

    @Provides
    @Singleton
//    @Named("OBJECT_VALIDATOR")
    public Validator<Object> getObjectValidator() {
        return new ObjectValidatorImpl();
    }

    @Provides
    @Singleton
//    @Named("OBJECT_VALIDATOR")
    public Validator<String> getStringValidator() {
        return new StringValidatorImpl();
    }

    @Provides
    @Singleton
//    @Named("OBJECT_VALIDATOR")
    public Validator<TaskUpdate> getTaskUpdateValidator() {
        return new TaskUpdateValidatorImpl();
    }
}
