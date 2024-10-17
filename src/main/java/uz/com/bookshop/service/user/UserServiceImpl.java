package uz.com.bookshop.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.exception.NotAcceptableException;
import uz.com.bookshop.exception.UserBadRequestException;
import uz.com.bookshop.model.dto.request.LoginDto;
import uz.com.bookshop.model.dto.request.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.dto.response.user.JwtResponse;
import uz.com.bookshop.model.dto.response.user.UserForFront;
import uz.com.bookshop.model.entity.user.Gender;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.model.entity.user.UserRole;
import uz.com.bookshop.repository.UserRepository;
import uz.com.bookshop.service.auth.JwtService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;




    @Override
    public StandardResponse<JwtResponse> signUp(UserDto userDto) {
        checkHasUsernameAndPassword(userDto.getUsername(),userDto.getPassword());
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        try {
            userEntity.setGender(Gender.valueOf(userDto.getGender()));
        }catch (Exception e){
            throw new NotAcceptableException("Gender not found");
        }
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(UserRole.USER);
        userEntity.setFullName(userDto.getFullName());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        userEntity.setAge(Period.between(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),LocalDate.now()).getYears());
        userRepository.save(userEntity);
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        UserForFront userForFront = modelMapper.map(userEntity, UserForFront.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .userForFront(userForFront)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return StandardResponse.<JwtResponse>builder()
                .data(jwtResponse)
                .status(Status.SUCCESS)
                .message("Sign up successfully!")
                .build();
    }







    @Override
    public StandardResponse<JwtResponse> signIn(LoginDto loginDto) {

        Optional<UserEntity> userEntity = userRepository.findUserEntityByUsername(loginDto.getUsername());
        if (userEntity.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), userEntity.get().getPassword())){
            String accessToken = jwtService.generateAccessToken(userEntity.get());
            String refreshToken = jwtService.generateRefreshToken(userEntity.get());
            UserForFront userForFront =  modelMapper.map(userEntity, UserForFront.class);
            JwtResponse jwtResponse = JwtResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(accessToken)
                    .userForFront(userForFront)
                    .build();
            return StandardResponse.<JwtResponse>builder()
                    .data(jwtResponse)
                    .status(Status.SUCCESS)
                    .message("Sign in successfully!")
                    .build();
        }
        else {
            throw new UserBadRequestException("Something error during sign in!");
        }
    }







    private void checkHasUsernameAndPassword(String username, String phoneNumber) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByUsername(username);
        if (userEntity.isPresent()){
            throw new DataNotFoundException("User has already exist");
        }
        if (userRepository.findUserEntityByPhoneNumber(phoneNumber).isPresent()){
            throw new DataNotFoundException("User has already exist");
        }
    }




}
