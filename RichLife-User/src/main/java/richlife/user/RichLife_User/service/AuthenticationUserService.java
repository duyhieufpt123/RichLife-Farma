package richlife.user.RichLife_User.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import richlife.user.RichLife_User.dto.LoginResponseDTO;
import richlife.user.RichLife_User.dto.RegisterRequest;
import richlife.user.RichLife_User.dto.ResponseData;
import richlife.user.RichLife_User.dto.request.LoginRequest;

import java.io.IOException;

public interface AuthenticationUserService {
    /**
     * Login response data.
     *
     * @param request the request
     * @return the response data
     */
    ResponseData<LoginResponseDTO> login(LoginRequest request);

    /**
     * Register response data.
     *
     * @param request the request
     * @return the response data
     */
    ResponseData<?> register(RegisterRequest request);

//    /**
//     * Verify account response data.
//     *
//     * @param verifyAccountRequestDTO the verify account request dto
//     * @return the response data
//     */
//    ResponseData<?> verifyAccount(VerifyAccountRequestDTO verifyAccountRequestDTO);
//
//    /**
//     * Regenerate otp response data.
//     *
//     * @param requestDTO the request dto
//     * @return the response data
//     */
//    ResponseData<?> regenerateOtp(RegenerateOTPRequestDTO requestDTO);

//    /**
//     * Change password response data.
//     *
//     * @param changePasswordRequestDTO the change password request dto
//     * @param principal                the principal
//     * @return the response data
//     */
//    ResponseData<?> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, Principal principal);

//    /**
//     * Check email existed response data.
//     *
//     * @param requestDTO the request dto
//     * @return the response data
//     */
//    ResponseData<User> checkEmailExisted(EmailRequestDTO requestDTO);

    /**
     * Refresh token.
     *
     * @param request  the request
     * @param response the response
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}