package se.lexicon.booklender.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;
    @ManyToOne
    @JoinColumn(columnDefinition = "user_id")
    private LibraryUser loanTaker;
    @ManyToOne
    @JoinColumn(columnDefinition = "book_id")
    private Book book;
    private LocalDate loanDate;
    private boolean terminated;
}



