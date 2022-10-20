package com.library.loan.service;
import com.library.loan.dto.LoanDto;
import com.library.loan.repository.LoanRepository;
import com.library.exceptions.CustomNotFoundException.LoanNotFoundException;
import com.library.loan.model.Loan;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

   private LoanRepository loanRepository;
    EntityManager entityManager;
    private ModelMapper modelMapper;

    public LoanServiceImpl(LoanRepository loanRepository,EntityManager entityManager,ModelMapper modelMapper) {
        this.loanRepository = loanRepository;
        this.entityManager=entityManager;
        this.modelMapper=modelMapper;
    }

    public List<LoanDto> getAllMembersBook() {
        return loanRepository.findAll().stream().map(this::buildLoanDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanDto saveLoan(LoanDto loanDto) {
        Loan loan= loanRepository.saveAndFlush(buildLoan(loanDto));
        entityManager.refresh(loan);
        System.out.println(loan);
        return buildLoanDto(loan);
    }

    @Override
    public LoanDto getLoan(Long id) {
       Optional<Loan> loan = loanRepository.findById(id);
        if(loan.isPresent()) {
           return buildLoanDto(loan.get());
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

    public LoanDto buildLoanDto(Loan loan)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LoanDto loanDto = modelMapper.map(loan,LoanDto.class);
        return loanDto;
    }

    public Loan buildLoan(LoanDto loanDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Loan loan = modelMapper.map(loanDto,Loan.class);
        return loan;
    }
}
