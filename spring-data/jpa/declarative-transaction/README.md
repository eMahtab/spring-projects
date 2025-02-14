# Declarative Transaction Management

## Project Structure

!["Project"](images/project.png)

## Handling Transaction Programmatically using TransactionTemplate

**Any RuntimeException or Error triggers rollback, and any checked Exception does not.**

```java
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(Account::getBalance)
                .orElseThrow(() -> new AccountNumberNotFoundException("Account number not found"));
    }

    @Transactional
    public void transferAmount(Long fromAccountId, Long toAccountId, BigDecimal amount) {
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
    }

    @Transactional
    public Account createAccount(Account account) {
        Optional<User> user = userRepository.findById(account.getUserId());
        if(user.isEmpty())
             throw new RuntimeException("User not found");

        Long newAccountNumber = generateUniqueAccountNumber();
        account.setAccountNumber(newAccountNumber);
        Account newAccount = accountRepository.save(account);
        user.get().getAccounts().add(newAccount);
        userRepository.save(user.get());
        return newAccount;
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
```

