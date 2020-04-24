package com.qa.tapiwa.spring;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.tapiwa.spring.data.domain.Account;
import com.qa.tapiwa.spring.dto.AccountDto;
import com.qa.tapiwa.spring.services.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Account account = new Account("test", "testy");
    private AccountDto accountDto = new AccountDto(1L, account.getFirstName(), account.getLastName());
    @Autowired
    private AccountService accountService;


    @Test
    public void create() throws Exception {

        String dtoString = objectMapper.writeValueAsString(accountDto);

        RequestBuilder request = request(HttpMethod.POST, "/account/create")
                .content(objectMapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON);


        String responseBody = this.mockMvc
                .perform(request)
                .andReturn().getResponse().getContentAsString();
        Account createdAccount = this.objectMapper.readValue(responseBody, Account.class);

        assertEquals(account.getFirstName(), createdAccount.getFirstName());


    }

    @Test
    public void delete() throws Exception {


        long id = this.accountService.create(account).getId();
        RequestBuilder rq = request(HttpMethod.DELETE, "/account/delete/" + id);


        String rs = this.mockMvc.perform(rq).andReturn().getResponse().getContentAsString();

        assertFalse(Boolean.valueOf(rs));


    }

    @Test

    public void read() throws Exception {

        AccountDto ac = this.accountService.create(account);


        RequestBuilder rq = request(HttpMethod.GET, "/account/read");

        String rs = this.mockMvc.perform(rq).andReturn().getResponse().getContentAsString();


        AccountDto[] accounts = this.objectMapper.readValue(rs, AccountDto[].class);
        List<AccountDto> accountList = Stream.of(accounts).collect(Collectors.toList());

        assertTrue(accountList.contains(ac));


    }

    @Test
    public void update() throws Exception {

        AccountDto acc = this.accountService.create(account);

        RequestBuilder rq = request(HttpMethod.PUT, "/account/update/" + acc.getId() + "?firstName=Update&lastName=Updated");

        String rs = this.mockMvc.perform(rq).andReturn().getResponse().getContentAsString();

        Account updated = this.objectMapper.readValue(rs, Account.class);
        System.out.println(account.getFirstName());
        assertFalse(account.getFirstName() == updated.getFirstName());

    }


}


