package org.example.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.example.domain.Loan;
import org.example.model.LoanRequest;
import org.example.repo.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessRequestDelegate implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(ProcessRequestDelegate.class);
    private final LoanRepository repository;

    public ProcessRequestDelegate(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LoanRequest loanRequest = (LoanRequest) execution.getVariable("loan");
        logger.info("Process request {}", loanRequest);

        Loan loan = new Loan();
        loan.setAmount(loanRequest.getAmount());
        loan.setCustomerId(loanRequest.getCustomerId());
        loan.setStatus("APPROVED");

        repository.save(loan);
    }
}
