package com.example.JUnitTest.web;

import com.example.JUnitTest.dto.BookEditDto;
import com.example.JUnitTest.dto.BookSaveDto;
import com.example.JUnitTest.service.BookService;
import com.example.JUnitTest.web.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<Long> saveBook(@RequestBody BookSaveDto saveDto) {
        return new ResponseEntity<>(bookService.saveBook(saveDto), HttpStatus.CREATED);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Long> editBook(@PathVariable Long id, @RequestBody BookEditDto editDto) {
        return new ResponseEntity<>(bookService.editBook(editDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
