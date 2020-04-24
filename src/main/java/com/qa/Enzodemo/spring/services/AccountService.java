package com.qa.tapiwa.spring.services;

import com.qa.tapiwa.spring.data.domain.Account;
import com.qa.tapiwa.spring.data.repo.AccountRepo;
import com.qa.tapiwa.spring.dto.AccountDto;
import com.qa.tapiwa.spring.exceptions.AccountNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepo accountRepo;
    private ModelMapper mapper;


    public AccountService(AccountRepo accountRepo, ModelMapper mapper) {
        this.accountRepo = accountRepo;
        this.mapper = mapper;

    }

    private AccountDto mapToDto(Account account){
        return  this.mapper.map(account, AccountDto.class);
    }

    public AccountDto create(Account account){
        Account saveAccount = this.accountRepo.save(account);
        AccountDto dto = mapToDto(saveAccount);
        return dto;
    }

    public List<AccountDto> read(){
        return this.accountRepo.findAll().stream().map((account) -> this.mapToDto(account)).collect(Collectors.toList());
    }

    public Account update(String firstName, String lastName, Long id){
        Account account = this.accountRepo.findById(id).orElseThrow(() -> new AccountNotFoundException());
        account.setFirstName(firstName);
        account.setLastName(lastName);
        return  this.accountRepo.save(account);

    }


    public  boolean delete(long id){
        this.accountRepo.deleteById(id);
        return this.accountRepo.existsById(id);
    }
}
