package uz.com.bookshop.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.user.LoginDto;
import uz.com.bookshop.model.dto.request.user.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.user.JwtResponse;
import uz.com.bookshop.model.dto.response.user.UserForFront;

import java.security.Principal;
import java.util.UUID;

@Service
public interface UserService {


    StandardResponse<JwtResponse> signUp(UserDto userDto);


    StandardResponse<JwtResponse> signIn(LoginDto loginDto);


    StandardResponse<String> delete(UUID id, Principal principal);


    StandardResponse<UserForFront> getById(UUID id);


    Page<UserForFront> getAllUsers(Pageable pageable);


    StandardResponse<UserForFront> assignToAdmin(UUID id);


    StandardResponse<UserForFront> update(UserDto userDto, UUID id);
}
