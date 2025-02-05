package app.wallet.service;
import app.exception.DomainException;
import app.transaction.model.Transaction;
import app.transaction.model.TransactionStatus;
import app.transaction.model.TransactionType;
import app.transaction.service.TransactionService;
import app.user.model.User;
import app.wallet.model.Wallet;
import app.wallet.model.WalletStatus;
import app.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    public WalletService(WalletRepository walletRepository,
                         TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    public Wallet createWallet(User user){

        return this.walletRepository.save(Wallet.builder()
                .balance(BigDecimal.valueOf(20))
                .status(WalletStatus.ACTIVE)
                .currency(Currency.getInstance("EUR"))
                .owner(user)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build());
    }

    @Transactional
    public Transaction topUp(UUID walletId,BigDecimal amount){
        Wallet wallet = this.walletRepository
                .findById(UUID.randomUUID())
                .orElseThrow(() -> new DomainException("Wallet with [%s] does not exist.".formatted(walletId.toString())));

        if(wallet.getStatus() == WalletStatus.INACTIVE){
            return this.transactionService.createTransaction(wallet.getOwner(),
                    "Wallet Group",
                    walletId.toString(),
                    amount,
                    wallet.getBalance(),
                    wallet.getCurrency(),
                    TransactionType.DEPOSIT,
                    TransactionStatus.FAILED,
                    "Transfer money.",
                    "Wallet status inactive.",
                    LocalDateTime.now()
                    );
        }
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedOn(LocalDateTime.now());
        this.walletRepository.save(wallet);

        return this.transactionService.createTransaction(wallet.getOwner(),
                "Waller Group",
                walletId.toString(),
                amount,
                wallet.getBalance(),
                wallet.getCurrency(),
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCEEDED,
                "Transfer money",
                null,
                LocalDateTime.now());
    }
}