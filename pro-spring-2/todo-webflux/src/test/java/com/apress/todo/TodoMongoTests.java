package com.apress.todo;

import com.apress.todo.domain.ToDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class TodoMongoTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void toDoMongoTest() {
        ToDo toDo = new ToDo("Read a Book");
        this.mongoTemplate.save(toDo);
        ToDo result = this.mongoTemplate.findById(toDo.getId(),ToDo.class);
        assertThat(result.getId()).isEqualTo(toDo.getId());
    }
}
