package se.lexicon.booklender;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int bookId;
private String title;
private boolean available;
private boolean reserved;
private int maxLoanDays;
private BigDecimal finePerDay;
private String description;
}
