package se.lexicon.booklender.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDto {
    private long loanId;
    private LibraryUserDto loanTaker;
    private BookDto book;
    private LocalDate loanDate;
    private boolean terminated;
}
