package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.exception.RecordNotFoundException;
import se.lexicon.booklender.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    LoanService loanService;

@Autowired
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }
    @GetMapping
    public ResponseEntity<List<LoanDto>>findAll(){
    return ResponseEntity.status(HttpStatus.OK).body(loanService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<LoanDto>findById(@PathVariable("id")long loanId)throws RecordNotFoundException{
    return ResponseEntity.status(HttpStatus.OK).body(loanService.findById(loanId));
    }
    @PostMapping
    public ResponseEntity<LoanDto>create(@RequestBody LoanDto loanDto){
    if (loanDto==null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(loanDto));
    }
    @PutMapping
    public ResponseEntity<LoanDto>update(@RequestBody LoanDto loanDto)throws RecordNotFoundException {
    if (loanDto==null){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(loanService.update(loanDto));
    }

}

