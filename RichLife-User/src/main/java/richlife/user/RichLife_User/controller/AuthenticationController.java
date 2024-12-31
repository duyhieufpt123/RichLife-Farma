package richlife.user.RichLife_User.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import richlife.user.RichLife_User.config.LogoutConfig;
import richlife.user.RichLife_User.constants.ResponseCode;
import richlife.user.RichLife_User.dto.LoginResponseDTO;
import richlife.user.RichLife_User.dto.RegisterRequest;
import richlife.user.RichLife_User.dto.ResponseData;
import richlife.user.RichLife_User.dto.request.LoginRequest;
import richlife.user.RichLife_User.service.AuthenticationUserService;
import richlife.user.RichLife_User.service.UserService;
import richlife.user.RichLife_User.service.impl.CustomUserDetailsService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationUserService authenticationUserService;
    private final LogoutConfig logoutConfig;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    /**
     * Login response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseData<LoginResponseDTO>> login(@RequestBody @Valid LoginRequest request) {
//        String username = request.getUsername();
//        String password = request.getPassword();
//
//        Authentication authentication = authenticate(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (request == null) {
            new ResponseEntity<ResponseData<LoginResponseDTO>>(HttpStatus.BAD_REQUEST);
        }
        ResponseData<LoginResponseDTO> loginAccount = authenticationUserService.login(request);
        if (loginAccount.getStatus() == ResponseCode.C200.getCode()) {
            return ResponseEntity.status(HttpStatus.OK).body(loginAccount);
        } else if (loginAccount.getStatus() == ResponseCode.C203.getCode()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginAccount);
        } else if (loginAccount.getStatus() == ResponseCode.C201.getCode()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginAccount);
        } else if (loginAccount.getStatus() == ResponseCode.C209.getCode()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginAccount);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginAccount);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<?>> register(@RequestBody @Valid RegisterRequest request) {
        if (request == null) {
            new ResponseEntity<ResponseData<?>>(HttpStatus.BAD_REQUEST);
        }
        ResponseData<?> registerAccount = authenticationUserService.register(request);
        if (registerAccount.getStatus() == ResponseCode.C200.getCode()) {
            return ResponseEntity.status(HttpStatus.OK).body(registerAccount);
        } else if (registerAccount.getStatus() == ResponseCode.C203.getCode()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerAccount);
        } else if (registerAccount.getStatus() == ResponseCode.C201.getCode()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(registerAccount);
        } else if (registerAccount.getStatus() == ResponseCode.C209.getCode()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(registerAccount);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registerAccount);
    }


    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationUserService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "BearerAuth")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutConfig.logout(request, response, authentication);
    }


    private Authentication authenticate(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return authentication;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
    }

}