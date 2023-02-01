package com.example.JUnitTest.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    /*
    * 책 저장 테스트
    * */
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


        Assertions.assertThat(newBook).isEqualTo(book);
    }

    /*
    * 책 전체 조회 테스트
    * */
    @Test
    @Sql("classpath:templates/table.sql")
    public void findAllTest() {
        //given
        Book bookItem1 = Book.builder()
                .title("Test Title1")
                .content("Test Content1")
                .author("Writer1")
                .build();

        Book bookItem2 = Book.builder()
                .title("Test Title2")
                .content("Test Content2")
                .author("Writer2")
                .build();

        //when
        bookRepository.save(bookItem1);
        bookRepository.save(bookItem2);

        List<Book> bookList = bookRepository.findAll();

        //then
        Assertions.assertThat(bookList.size()).isEqualTo(2);
    }

    /*
    * 책 단일 조회 테스트
    * */
    @Test
    @Sql("classpath:templates/table.sql")
    public void findBookTest() {
        //given
        Long bookId = 1L;

        Book book = Book.builder()
                .title("Test title")
                .content("Test content")
                .author("Writer")
                .build();

        bookRepository.save(book);

        Book cpyBook = Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .content(book.getContent())
                .author(book.getAuthor())
                .build();

        //when
        Optional<Book> findItem = bookRepository.findById(bookId);

        //then
        Assertions.assertThat(findItem).isPresent();
        Assertions.assertThat(findItem.get()).isEqualTo(cpyBook);
    }

    /*
    * 책 삭제 테스트
    * */
    @Test
    @Sql("classpath:templates/table.sql")
    public void deleteBookTest() {
        //given
        long bookId = 1L;

        Book bookItem1 = Book.builder()
                .title("Test Title1")
                .content("Test Content1")
                .author("Writer1")
                .build();

        Book bookItem2 = Book.builder()
                .title("Test Title2")
                .content("Test Content2")
                .author("Writer2")
                .build();

        bookRepository.save(bookItem1);
        bookRepository.save(bookItem2);

        //when
        bookRepository.deleteById(bookId);

        //then
        Optional<Book> findBook = bookRepository.findById(1L);

        Assertions.assertThat(findBook).isNotPresent();
    }

    /*
    * 책 수정 테스트
    * */
    @Test
    @Sql("classpath:templates/table.sql")
    public void bookEditTest() {
        //given
        long bookId = 1L;

        Book bookItem = Book.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .author("Writer")
                .build();

        bookRepository.save(bookItem);

        //when
        Book book = bookRepository.findById(bookId).get();

        book.setTitle("수정 후 제목");
        book.setContent("수정 후 내용");

        Book cpyBook = Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .content(book.getContent())
                .author(book.getAuthor())
                .build();

        Book changedBook = bookRepository.findById(bookId).get();

        //then
        Assertions.assertThat(changedBook).isEqualTo(cpyBook);
    }
}
