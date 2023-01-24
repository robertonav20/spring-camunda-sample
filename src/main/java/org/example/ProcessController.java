package org.example;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.example.model.LoanRequest;
import org.example.model.Process;
import org.example.repo.LoanRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private final RuntimeService runtimeService;
    private final LoanRepository repository;

    public ProcessController(RuntimeService runtimeService, LoanRepository repository) {
        this.runtimeService = runtimeService;
        this.repository = repository;
    }

    @PutMapping("start")
    public Object start(@RequestBody LoanRequest request) {
        VariableMap variables = Variables.createVariables().putValue("loan", request);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("loan-approval", variables);
        return new Process(processInstance.getId());
    }

    @GetMapping("loans")
    public Object loans() {
        return repository.findAll();
    }
}
