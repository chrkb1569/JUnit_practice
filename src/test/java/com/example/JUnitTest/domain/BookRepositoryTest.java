package com.example.JUnitTest.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveTest() {
        String title = "테스트 도서 제목";
        String content  = "테스트 도서 내용";
        String author = "테스트 도서 저자";

        Book book = Book.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        Book savedBook = bookRepository.save(book);

        Book newBook = Book.builder()
                .id(savedBook.getId())
                .title(savedBook.getTitle())
                .content(savedBook.getContent())
                .author(savedBook.getAuthor())
                .build();


        Assertions.assertThat(newBook).isSameAs(book);
    }
}
