package com.library.loan.service;
import com.library.loan.dto.LoanDto;
import com.library.loan.model.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDto> getAllMembersBook();
    LoanDto saveLoan(LoanDto loanDto);
    LoanDto getLoan(Long id);
    void deleteLoan(Long id);
}
