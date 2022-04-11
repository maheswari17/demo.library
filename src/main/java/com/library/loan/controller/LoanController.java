package com.library.loan.controller;
import com.library.loan.dto.LoanDto;
import com.library.loan.model.Loan;
import com.library.loan.service.LoanServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/loans")
public class LoanController {
    LoanServiceImpl loanService;

    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/member-books")
    public List<LoanDto> getAllMemberBooks() {
        return loanService.getAllMembersBook();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable Long id) {
        return new ResponseEntity<>(loanService.getLoan(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public LoanDto saveLoan(@RequestBody LoanDto loanDto) {
        return loanService.saveLoan(loanDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "loan details deleted", code = HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
