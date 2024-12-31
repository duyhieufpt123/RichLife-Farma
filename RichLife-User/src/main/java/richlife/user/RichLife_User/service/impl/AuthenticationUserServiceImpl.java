package richlife.user.RichLife_User.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import richlife.user.RichLife_User.constants.*;
import richlife.user.RichLife_User.dto.LoginResponseDTO;
import richlife.user.RichLife_User.dto.RegisterRequest;
import richlife.user.RichLife_User.dto.ResponseData;
import richlife.user.RichLife_User.dto.request.LoginRequest;
import richlife.user.RichLife_User.dto.response.UserInfoResponseDTO;
import richlife.user.RichLife_User.entities.User;
import richlife.user.RichLife_User.entities.UserInfo;
import richlife.user.RichLife_User.entities.UserToken;
import richlife.user.RichLife_User.repository.UserInfoRepository;
import richlife.user.RichLife_User.repository.UserRepository;
import richlife.user.RichLife_User.repository.UserTokenRepository;
import richlife.user.RichLife_User.service.AuthenticationUserService;
import richlife.user.RichLife_User.service.JwtService;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationUserServiceImpl implements AuthenticationUserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public ResponseData<LoginResponseDTO> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user =  userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Người dùng không được tìm thấy !"));

            if (user == null) {
                return ResponseData.error("Bad request");
            } else if (user.getStatus().equals(AccountStatus.INACTIVE)) {
                return new ResponseData<>(ResponseCode.C209.getCode(), "Tài khoản đã bị khoá trong hệ thống");
            }
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            // TODO: Handle multiple device access account
//            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken, refreshToken);
            if (user.getRole() == Role.ADMIN && (request.getEmail().equals(user.getEmail()) || request.getEmail().equals(user.getEmail()))
                    && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return new ResponseData<>(ResponseCode.C200.getCode(), "Đăng nhập thành công", LoginResponseDTO.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .user(modelMapper.map(user, LoginResponseDTO.UserLoginResponseDTO.class))
                        .build());
            } else {
                var userInfo = userInfoRepository.findUserInfoById(user.getId());

                // Initialize response DTOs
                UserInfoResponseDTO userInfoResponseDTO = null;

                // Map userInfo with custom info
                if (userInfo != null) {
                    userInfoResponseDTO = modelMapper.map(userInfo, UserInfoResponseDTO.class);
                }
                return new ResponseData<>(ResponseCode.C200.getCode(), "Đăng nhập thành công", LoginResponseDTO.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .user(modelMapper.map(user, LoginResponseDTO.UserLoginResponseDTO.class))
                        .userInfo(userInfoResponseDTO)
                        .build());
            }

        } catch (Exception ex) {
            // Case 1: Bad Credential: Authentication Failure: 401
            // Case 2: Access Denied : Authorization Error: 403
            log.error("Error occurred while login: {}", ex.getMessage());
            return new ResponseData<>(ResponseCode.C201.getCode(), "Tên đăng nhập hoặc mật khẩu không đúng");
        }
    }

    @Override
    @Transactional
    public ResponseData<?> register(RegisterRequest request) {
        try {
            Optional<User> existEmail = userRepository.findByEmail(request.getEmail());
            if (existEmail.isPresent()) {
                return new ResponseData<>(ResponseCode.C205.getCode(), "Email đã được sử dụng " +request.getEmail());
            }

            Optional<User> existUser = userRepository.findByEmail(request.getEmail());
            if (existUser.isPresent()) {
                return new ResponseData<>(ResponseCode.C205.getCode(), "Username đã được sử dụng " +request.getEmail());
            }

            Optional<UserInfo> existPhone = userInfoRepository.findByPhone(request.getPhone());
            if (existPhone.isPresent()) {
                return new ResponseData<>(ResponseCode.C205.getCode(), "Số điện thoại đã được sử dụng "+ request.getPhone());
            }

            User newUser = new User();
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setRole(Role.USER);
            newUser.setStatus(AccountStatus.ACTIVE);
            newUser.setCreateTime(new Date());
            userRepository.save(newUser);

            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setId(newUser.getId());
            newUserInfo.setPhone(request.getPhone());
            newUserInfo.setGender(Gender.valueOf(request.getGender()));
            newUserInfo.setBirthday(request.getBirthday());
            userInfoRepository.save(newUserInfo);
            return new ResponseData<>(ResponseCode.C200.getCode(), "Tạo tài khoản thành công !");
        } catch (Exception e){
            log.error("Error occurred while register: {}", e.getMessage());
            return new ResponseData<>(ResponseCode.C210.getCode(), "Đã có lỗi xảy ra trong quá trình tạo tài khoản, vui lòng thử lại sau");
        }
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String oldRefreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        oldRefreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(oldRefreshToken);
        if (email != null) {
            var user = this.userRepository.findFirstByEmail(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(oldRefreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                // find old token and update it
                saveRefreshToken(user, oldRefreshToken, accessToken, newRefreshToken);
                var authResponse = LoginResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveRefreshToken(User user, String oldRefreshToken, String accessToken, String newRefreshToken) {
        UserToken token = userTokenRepository.findUserTokenByRefreshToken(oldRefreshToken);
        token.setUser(user);
        token.setToken(accessToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        token.setRefreshToken(newRefreshToken);
        userTokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = userTokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        userTokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken, String refreshToken) {
        var token = UserToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .refreshToken(refreshToken)
                .build();
        userTokenRepository.save(token);
    }
}
