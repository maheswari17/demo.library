package com.library.loan.service;
import com.library.loan.dto.LoanDto;
import com.library.loan.repository.LoanRepository;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.loan.model.Loan;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanDto> getAllMembersBook() {
        return loanRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public LoanDto saveLoan(LoanDto loanDto) {
        Loan loan= loanRepository.save(convertDtoToEntity(loanDto));
        return convertEntityToDto(loan);
    }

    @Override
    public Loan getLoan(Long id) {
       // Optional<Loan> loan = loanRepository.findById(id);
        //if(loan.isPresent()) {
          // return convertEntityToDto(loan.get());
        //}throw new LoanNotFoundException("loan details not found");
   return loanRepository.findById(id).orElseThrow(()->new LoanNotFoundException("loan details not found"));
    }

    @Override
    public void deleteLoan(Long id) {
        try {
            loanRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new LoanNotFoundException("loan details with " + id + " not found");
        }
    }

    private LoanDto convertEntityToDto(Loan loan)   {
        LoanDto loanDto = new LoanDto();
        loanDto.setMemberId(loan.getMemberId());
        loanDto.setLoanId(loan.getLoanId());
        loanDto.setBookId(loan.getBookId());
        loanDto.setIssueDate(loan.getIssueDate());
        loanDto.setReturnDate(loan.getReturnDate());
        return loanDto;
    }

    private Loan convertDtoToEntity(LoanDto loanDto)  {
        Loan loan = new Loan();
        loan.setMemberId(loanDto.getMemberId());
        loan.setLoanId(loanDto.getLoanId());
        loan.setBookId(loanDto.getBookId());
        loan.setIssueDate(loanDto.getIssueDate());
        loan.setReturnDate(loanDto.getReturnDate());
        return loan;
    }
}
