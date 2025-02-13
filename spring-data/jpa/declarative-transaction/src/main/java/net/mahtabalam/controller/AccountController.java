package net.mahtabalam.controller;

import net.mahtabalam.dto.TransferRequest;
import net.mahtabalam.entity.Account;
import net.mahtabalam.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAlAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}/balance")
    public BigDecimal getBalance(@PathVariable Long accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @PostMapping("/transfer")
    public String transferAmount(@RequestBody TransferRequest request) {
        accountService.transferAmount(request.fromAccountNumber(), request.toAccountNumber(), request.amount());
        return "Transfer successful";
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }


}
