package com.example.JUnitTest.service;

import com.example.JUnitTest.domain.Book;
import com.example.JUnitTest.domain.BookRepository;
import com.example.JUnitTest.dto.BookEditDto;
import com.example.JUnitTest.dto.BookSaveDto;
import com.example.JUnitTest.web.dto.BookResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 저장 테스트")
    @Transactional
    public void saveBook() {
        // given
        BookSaveDto bookSaveDto = new BookSaveDto("테스트 제목 1", "테스트 내용 1", "테스트 작성자 1");
        Book saveItem = bookSaveDto.toDto();

        //stub
        given(bookRepository.save(saveItem)).willReturn(bookSaveDto.toDto());

        //when
        Long savedId = bookService.saveBook(bookSaveDto);

        //then
        Assertions.assertThat(saveItem.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName(value = "책 전체 조회 테스트")
    @Transactional(readOnly = true)
    public void getBooksTest() {
        //given
        List<Book> bookList = new LinkedList<>();
        bookList.add(new Book(1L, "Test Title1", "Test Content1", "Test1"));
        bookList.add(new Book(2L, "Test Title2", "Test Content2", "Test2"));
        bookList.add(new Book(3L, "Test Title3", "Test Content3", "Test3"));

        List<Book> savedList = bookList;

        //stub
        given(bookRepository.findAll()).willReturn(savedList);

        //when
        List<BookResponseDto> resultList = bookService.getBooks();

        //then
        Assertions.assertThat(resultList.get(1).getContent()).isEqualTo(bookList.get(1).getContent());
    }

    // 책 단일 조회
    @Test
    @Transactional(readOnly = true)
    @DisplayName(value = "책 단일 조회 테스트")
    public void getBook() {
        //given
        Long bookId = 2L;

        Book savedBook = new Book(bookId, "Test Title2", "Test Content2", "Test2");

        //stub
        given(bookRepository.findById(bookId)).willReturn(Optional.of(savedBook));

        //when
        BookResponseDto findBook = bookService.getBook(2L);

        //then
        Assertions.assertThat(findBook.getTitle()).isEqualTo(savedBook.getTitle());
        Assertions.assertThat(findBook.getContent()).isEqualTo(savedBook.getContent());
        Assertions.assertThat(findBook.getAuthor()).isEqualTo(savedBook.getAuthor());
    }

    // 책 수정
    @Test
    @Transactional
    @DisplayName(value = "책 수정 테스트")
    public void editBook() {
        //given
        Long bookId = 1L;

        Book savedBook = Book.builder()
                .id(bookId)
                .title("Origin Title")
                .content("Origin Content")
                .author("Origin Author")
                .build();

        BookEditDto editDto = BookEditDto.builder()
                .title("Edit Title")
                .content("Edit Content")
                .build();

        //stub
        given(bookRepository.findById(bookId)).willReturn(Optional.of(savedBook));

        //when
        bookService.editBook(editDto, bookId);
        BookResponseDto findBook = bookService.getBook(bookId);

        //then
        Assertions.assertThat(findBook.getTitle()).isEqualTo(editDto.getTitle());
        Assertions.assertThat(findBook.getContent()).isEqualTo(editDto.getContent());
    }

    // 책 삭제
    @Test
    @DisplayName(value = "책 삭제 테스트")
    @Transactional
    public void deleteBook() {
        //given
        Long bookId = 1L;

        //stub

        //when
        bookService.deleteBook(bookId);

        //then
        verify(bookRepository).deleteById(bookId);
    }
}
