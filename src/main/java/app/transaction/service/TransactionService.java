package app.transaction.service;
import app.transaction.model.Transaction;
import app.transaction.model.TransactionStatus;
import app.transaction.model.TransactionType;
import app.transaction.repository.TransactionRepository;
import app.user.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(User owner,
                                         String sender,
                                         String receiver,
                                         BigDecimal amount,
                                         BigDecimal amountLeft,
                                         Currency currency,
                                         TransactionType transactionType,
                                         TransactionStatus transactionStatus,
                                         String description,
                                         String failureReason,
                                         LocalDateTime createdOn){
        return null;
    }
}
