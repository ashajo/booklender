package se.lexicon.booklender.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(columnDefinition= "user_id")
    private LibraryUser loanTaker;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(columnDefinition= "book_id")
    private Book book;
    private LocalDate loanDate;
    private boolean terminated;

    public Loan() {
    }
    public Loan(long loanId,LibraryUser loanTaker,Book book,LocalDate loanDate){
        this(loanTaker, book,loanDate);
        this.loanId=loanId;
    }
    public Loan(LibraryUser loanTaker,Book book,LocalDate loanDate){
        this.loanTaker=loanTaker;
        this.book=book;
        this.loanDate=loanDate;
        this.terminated=false;
    }

    public LibraryUser getLoanTaker() {
    return loanTaker;
}
public void setLoanTaker(LibraryUser loanTaker){
    this.loanTaker=loanTaker;
}
public Book getBook(){
    return book;
}
public void setBook(Book book){
    if (!book.isAvailable()){
        throw new IllegalArgumentException("Book is not available");
    }
    book.setAvailable(false);
    this.book=book;
}
public boolean isTerminated(){
    return terminated;

}
public void returnBook(){
    this.book.setAvailable(true);
    this.terminated=true;

}
    public long getLoanId(){
    return loanId;
    }
    public LocalDate getLoanDate(){
    return loanDate;
    }
    public boolean isOverdue(){
    LocalDate lastDay=loanDate.plusDays(book.getMaxLoanDays());
    boolean overdue=LocalDate.now().isAfter(lastDay);
    return overdue;
    }
    public BigDecimal getFine(){
        Period period=Period.between(loanDate.plusDays(book.getMaxLoanDays()),LocalDate.now());
        int daysOverdue=period.getDays();
        BigDecimal fine= BigDecimal.ZERO;
        if (daysOverdue>0){
            fine=BigDecimal.valueOf(daysOverdue*book.getFinePerDay().longValue());
        }
        return fine;
    }
    public boolean extendLoan(LocalDate newLoanDate){
    if (book.isReserved()|| isOverdue()){
        return false;
    }
    if (loanDate.plusDays(book.getMaxLoanDays()).isAfter(newLoanDate)||loanDate.plusDays(book.getMaxLoanDays()).equals(newLoanDate)){
        this.loanDate=newLoanDate;
        return true;
    }
    return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanId == loan.loanId && terminated == loan.terminated && Objects.equals(loanTaker, loan.loanTaker) && Objects.equals(book, loan.book) && Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, terminated);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", terminated=" + terminated +
                '}';
    }
}
