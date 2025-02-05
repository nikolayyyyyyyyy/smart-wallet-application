package app.user.service;
import app.exception.DomainException;
import app.subscription.service.SubscriptionService;
import app.user.model.LoginRequest;
import app.user.model.RegisterRequest;
import app.user.model.User;
import app.user.model.UserRole;
import app.user.repository.UserRepository;
import app.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final SubscriptionService subscriptionService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       WalletService walletService,
                       SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
        this.subscriptionService = subscriptionService;
    }

    @Transactional
    public User register(RegisterRequest registerRequest){
        if(this.userRepository.findByUsername(registerRequest.getUsername()) != null){

            throw new DomainException("User with username %s already exist".formatted(registerRequest.getUsername()));
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .country(registerRequest.getCountry())
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        user.getWallets()
                .add(this.walletService.createWallet(user));
        user.getSubscriptions()
                .add(this.subscriptionService.createSubscription(user));

        return user;
    }

    public User login(LoginRequest loginRequest){
        if(this.userRepository.findByUsername(loginRequest.getUsername()) != null){

            throw new DomainException("Username or password not correct!");
        }

        User user = this.userRepository.findByUsername(loginRequest.getUsername());

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){

            throw new DomainException("Username or password not correct");
        }

        return user;
    }
}


















