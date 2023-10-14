package taskManagement.helper;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

@Getter
@Setter
public class MongoDBTask {
    @BsonProperty("_id")
    private String identifier;

    private String title;

    private String description;

    @BsonProperty("created_at")
    private long createdAt;

    private boolean completed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MongoDBTask that = (MongoDBTask) o;
        return createdAt == that.createdAt
                && completed == that.completed
                && Objects.equals(identifier, that.identifier)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, title, description, createdAt, completed);
    }
}
