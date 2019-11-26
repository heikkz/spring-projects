package com.apress.todo;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoSimpleMockBeanTests {

    @MockBean
    private ToDoRepository repository;

    @Test
    public void toDoTest() {
//        given(this.repository.findById("my-id"))
//                .willReturn(Mono.just(new ToDo("Do homework", true)));
//        assertThat(this.repository.findById("my-id").()).ie;
    }
}
