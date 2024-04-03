package kmd.backend.magazine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kmd.backend.magazine.dtos.AuthenticationResponse;
import kmd.backend.magazine.dtos.UserRequestDto;
import kmd.backend.magazine.exceptions.EntityAlreadyExistException;
import kmd.backend.magazine.models.Token;
import kmd.backend.magazine.models.User;
import kmd.backend.magazine.repos.TokenRepository;
import kmd.backend.magazine.repos.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    FacultyService facultyService;

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

    // public AuthenticationResponse register(UserRequestDto userRequestDto) throws Exception {
    //     User user = new User();
    //     List<User> existingUsers = repository.findByNameAndDeleteStatus(userRequestDto.getName(), false);
    //     if (existingUsers.isEmpty()) {

    //         try {
    //             if (userRequestDto.getProfilePhoto() != null && !userRequestDto.getProfilePhoto().isEmpty()) {
    //                 String fileName = StringUtils.cleanPath(userRequestDto.getProfilePhoto().getOriginalFilename());
    //                 if (fileName.contains("..")) {
    //                     throw new Exception("Filename contains invalid path sequence "
    //                             + fileName);
    //                 }
    //                 user.setProfilePhotoData(userRequestDto.getProfilePhoto().getBytes());
    //                 user.setProfilePhotoName(fileName);
    //                 user.setProfilePhotoType(userRequestDto.getProfilePhoto().getContentType());
    //             }
    //             user.setName(userRequestDto.getName());
    //             user.setRole(User.Role.valueOf(userRequestDto.getRole()));
    //             if (userRequestDto.getFaculty() != 0) {
    //                 user.setFaculty(facultyService.getFacultyById(userRequestDto.getFaculty()));
    //             }

    //             BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    //             user.setPassword(bcrypt.encode(userRequestDto.getPassword()));
    //              repository.save(user);
    //         } catch (IllegalArgumentException e) {
    //             throw new IllegalArgumentException("Invalid role provided.");
    //         } catch (Exception e) {
    //             throw new Exception(e.getMessage());
    //         }
    //     } else {
    //         throw new EntityAlreadyExistException("User is already added");
    //     }
        

    //     String jwt = jwtService.generateToken(user);

    //     saveUserToken(jwt, user);

    //     return new AuthenticationResponse(jwt, "User registration was successful");

    // }

    public AuthenticationResponse authenticate(String username, String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        User user = repository.findByName(username).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");

    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
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