package app.wallet.service;

import app.user.model.User;
import app.wallet.model.Wallet;
import app.wallet.model.WalletStatus;
import app.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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
}
