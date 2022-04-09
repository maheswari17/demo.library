package com.library.loan.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class LoanDto  {
    private long memberId;
    private long bookId;
    private long loanId;
    private LocalDate returnDate;
    private LocalDate issueDate;


}
