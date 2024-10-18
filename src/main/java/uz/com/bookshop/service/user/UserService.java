package uz.com.bookshop.service.user;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.user.LoginDto;
import uz.com.bookshop.model.dto.request.user.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.user.JwtResponse;

@Service
public interface UserService {

    StandardResponse<JwtResponse> signUp(UserDto userDto);

    StandardResponse<JwtResponse> signIn(LoginDto loginDto);
}
