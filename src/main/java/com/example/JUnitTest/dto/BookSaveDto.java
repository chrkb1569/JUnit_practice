package com.example.JUnitTest.dto;

import com.example.JUnitTest.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BookSaveDto {
    private String title;
    private String content;
    private String author;

    public Book toDto() {
        return Book.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .build();
    }
}
