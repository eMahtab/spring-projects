package net.mahtabalam.service;

import net.mahtabalam.entity.Account;
import net.mahtabalam.entity.User;
import net.mahtabalam.exception.AccountNumberNotFoundException;
import net.mahtabalam.exception.InsufficientFundsException;
import net.mahtabalam.repository.AccountRepository;
import net.mahtabalam.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionTemplate transactionTemplate;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository,
                          PlatformTransactionManager transactionManager) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(Account::getBalance)
                .orElseThrow(() -> new AccountNumberNotFoundException("Account number not found"));
    }


    public void transferAmount(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        transactionTemplate.executeWithoutResult(status -> {
            Account fromAccount = accountRepository.findByAccountNumber(fromAccountId)
                    .orElseThrow(() -> new AccountNumberNotFoundException("Source account not found"));
            Account toAccount = accountRepository.findByAccountNumber(toAccountId)
                    .orElseThrow(() -> new AccountNumberNotFoundException("Destination account not found"));

            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient balance");
            }

            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        });
    }
    public Account createAccount(Account account) {
        return transactionTemplate.execute(status -> {
            Optional<User> user = userRepository.findById(account.getUserId());
            if(user.isEmpty())
                throw new RuntimeException("User not found");

            Long newAccountNumber = generateUniqueAccountNumber();
            account.setAccountNumber(newAccountNumber);
            Account newAccount = accountRepository.save(account);
            user.get().getAccounts().add(newAccount);
            userRepository.save(user.get());
            return newAccount;
        });
    }

    private Long generateUniqueAccountNumber() {
        Long accountNumber;
        do {
            SecureRandom secureRandom = new SecureRandom();
            accountNumber = 1_000_000_000L + (long) (secureRandom.nextDouble() * 8_999_999_999L);
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());
        return accountNumber;
    }
}
