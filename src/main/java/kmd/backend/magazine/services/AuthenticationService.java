package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kmd.backend.magazine.dtos.AuthenticationResponse;
import kmd.backend.magazine.models.Token;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.TokenRepository;
import kmd.backend.magazine.repos.UserRepo;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    FacultyService facultyService;

    @Autowired
    CommonService commonService;

    public AuthenticationService(UserRepo repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));
        User user = repository.findByNameAndDeleteStatus(username,false).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        String downloadURL = commonService.fileDownloadURL("api/v1/user/profilePhoto", user.getProfilePhotoData(),
                user.getProfilePhotoName(), user.getId());
        return new AuthenticationResponse(user.getId(), user.getName(), user.getRole().toString(), user.getEmail(),
                downloadURL, user.isDeleteStatus(), user.getFaculty(), jwt);

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}