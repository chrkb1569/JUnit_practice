package com.example.JUnitTest.web.dto;

import com.example.JUnitTest.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BookResponseDto {
    private String title;
    private String content;
    private String author;

    public static BookResponseDto getInstance(Book book) {
        return BookResponseDto.builder()
                .title(book.getTitle())
                .content(book.getContent())
                .author(book.getAuthor())
                .build();
    }
}
