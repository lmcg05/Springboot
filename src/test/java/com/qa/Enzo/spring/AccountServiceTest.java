package com.qa.tapiwa.spring;


import com.qa.tapiwa.spring.data.domain.Account;
import com.qa.tapiwa.spring.data.repo.AccountRepo;
import com.qa.tapiwa.spring.dto.AccountDto;
import com.qa.tapiwa.spring.services.AccountService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {


    private Account account;
    private AccountDto accountDto;
    private long id = 1L;
    private Account accountwithId;

    @Before
    public void init() {

        this.account = new Account("test", "last");
        this.account.setId(id);
        this.accountwithId = new Account(this.account.getFirstName(), this.account.getLastName());
        this.accountwithId.setId(id);
        this.accountDto = new AccountDto(id, "test", "last");

    }

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountService accountService;

    @Test

    public void create() {
        when(accountRepo.save(account)).thenReturn(accountwithId);
        when(modelMapper.map(accountwithId, AccountDto.class)).thenReturn(accountDto);


        assertEquals(accountDto, accountService.create(account));

        verify(accountRepo, times(1)).save(account);
        verify(modelMapper, times(1)).map(accountwithId, AccountDto.class);


    }


}
