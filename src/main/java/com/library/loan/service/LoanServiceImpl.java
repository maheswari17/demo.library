package com.library.loan.service;
import com.library.loan.dto.LoanDto;
import com.library.loan.repository.LoanRepository;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.loan.model.Loan;
import com.library.utils.DtoConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    LoanRepository loanRepository;
    EntityManager entityManager;

    public LoanServiceImpl(LoanRepository loanRepository,EntityManager entityManager) {
        this.loanRepository = loanRepository;
        this.entityManager=entityManager;
    }

    public List<LoanDto> getAllMembersBook() {
        return loanRepository.findAll().stream().map(DtoConverter::buildLoanDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanDto saveLoan(LoanDto loanDto) {
        Loan loan= loanRepository.saveAndFlush(DtoConverter.buildLoan(loanDto));
        entityManager.refresh(loan);
        System.out.println(loan);
        return DtoConverter.buildLoanDto(loan);
    }

    @Override
    public LoanDto getLoan(Long id) {
       Optional<Loan> loan = loanRepository.findById(id);
        if(loan.isPresent()) {
           return DtoConverter.buildLoanDto(loan.get());
        }throw new LoanNotFoundException("loan details not found");
    }

    @Override
    public void deleteLoan(Long id) {
        try {
            loanRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new LoanNotFoundException("loan details with " + id + " not found");
        }
    }


}
