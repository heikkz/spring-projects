/*
 * STRICTLY CONFIDENTIAL
 * TRADE SECRET
 * PROPRIETARY:
 *       "BIFIT" JSC, TIN 7719617469
 *       105203, Russia, Moscow, Nizhnyaya Pervomayskaya, 46
 * (c) "BIFIT" JSC, 2019
 *
 * СТРОГО КОНФИДЕНЦИАЛЬНО
 * КОММЕРЧЕСКАЯ ТАЙНА
 * СОБСТВЕННИК:
 *       АО "БИФИТ", ИНН 7719617469
 *       105203, Россия, Москва, ул. Нижняя Первомайская, д. 46
 * (c) АО "БИФИТ", 2019
 */
package com.apress.todo;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebFluxTest(ToDoFluxController.class)
public class ToDoWebFluxTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private ToDoRepository toDoRepository;
    @Test
    public void testExample() throws Exception {
        given(this.toDoRepository.findAll())
                .Return(Arrays.asList(new ToDo("Read a Book"), new ToDo("Buy Milk")));
        this.webClient.get().uri("/todo-flux").accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class);
    }
}
