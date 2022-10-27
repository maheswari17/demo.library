package com.library.loan.model;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class LoanTest {

    @Test
    void testConstructor() {
        Loan actualLoan = new Loan();
        assertNull(actualLoan.getBook());
        assertNull(actualLoan.getMember());
    }
}

