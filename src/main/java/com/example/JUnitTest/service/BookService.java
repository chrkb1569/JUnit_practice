package com.example.JUnitTest.service;

import com.example.JUnitTest.domain.Book;
import com.example.JUnitTest.domain.BookRepository;
import com.example.JUnitTest.dto.BookEditDto;
import com.example.JUnitTest.dto.BookSaveDto;
import com.example.JUnitTest.web.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    // 책 전체 조회
    @Transactional(readOnly = true)
    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(s -> BookResponseDto.getInstance(s))
                .collect(Collectors.toList());
    }

    // 책 단일 조회
    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book findItem = bookRepository.findById(id).orElseThrow();

        return BookResponseDto.getInstance(findItem);
    }

    // 책 생성

    @Transactional
    public Long saveBook(BookSaveDto saveDto) {
        Book book = saveDto.toDto();

        Book savedBook = bookRepository.save(book);

        return savedBook.getId();
    }

    // 책 수정

    @Transactional
    public Long editBook(BookEditDto editDto, Long id) {
        Book findItem = bookRepository.findById(id).orElseThrow();

        findItem.setTitle(editDto.getTitle());
        findItem.setContent(editDto.getContent());

        return findItem.getId();
    }

    // 책 삭제
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
