package com.example.JUnitTest.web;

import com.example.JUnitTest.dto.BookEditDto;
import com.example.JUnitTest.dto.BookSaveDto;
import com.example.JUnitTest.service.BookService;
import com.example.JUnitTest.web.dto.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .build();
    }

    @Test
    @DisplayName("책 전체 조회 테스트")
    public void getBooksTest() throws Exception {
        //given
        List<BookResponseDto> resultList = new ArrayList<>();
        resultList.add(new BookResponseDto("Test Title1", "Test Content1", "Test Writer1"));
        resultList.add(new BookResponseDto("Test Title2", "Test Content2", "Test Writer2"));
        resultList.add(new BookResponseDto("Test Title3", "Test Content3", "Test Writer3"));

        //stub
        BDDMockito.given(bookService.getBooks()).willReturn(resultList);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(bookService).getBooks();
    }

    @Test
    @DisplayName("책 단일 조회 테스트")
    public void getBookTest() throws Exception {
        //given
        Long bookId = 1L;

        BookResponseDto responseDto =
                new BookResponseDto("Test Title1", "Test Content1", "Test Writer1");

        //stub
        BDDMockito.given(bookService.getBook(bookId)).willReturn(responseDto);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + bookId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(bookService).getBook(bookId);
    }

    @Test
    @DisplayName("책 저장 테스트")
    public void saveBookTest() throws Exception{
        //given
        BookSaveDto saveDto = new BookSaveDto("Test Title1", "Test Content1", "Test Writer1");

        //stub
        BDDMockito.given(bookService.saveBook(BDDMockito.any())).willReturn(BDDMockito.anyLong());

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(objectMapper.writeValueAsString(saveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        //then
        BDDMockito.verify(bookService).saveBook(BDDMockito.any());
    }

    @Test
    @DisplayName("책 수정 테스트")
    public void editBookTest() throws Exception{
        //given
        Long bookId = 1L;
        BookEditDto editDto = new BookEditDto("Edit Title", "Edit Content");

        //stub
        BDDMockito.given(bookService.editBook(editDto, bookId)).willReturn(bookId);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/book/" + bookId)
                .content(objectMapper.writeValueAsString(editDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(bookService).editBook(editDto, bookId);
    }

    @Test
    @DisplayName("책 삭제 테스트")
    public void deleteBookTest() throws Exception {
        //given
        Long bookId = 1L;

        //stub
        BDDMockito.willDoNothing().given(bookService).deleteBook(bookId);

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/" + bookId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then
        BDDMockito.verify(bookService).deleteBook(bookId);
    }
}
