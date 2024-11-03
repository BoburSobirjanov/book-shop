package uz.com.bookshop.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.exception.NotAcceptableException;
import uz.com.bookshop.exception.UserBadRequestException;
import uz.com.bookshop.mapper.UserMapper;
import uz.com.bookshop.model.dto.request.user.LoginDto;
import uz.com.bookshop.model.dto.request.user.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.user.JwtResponse;
import uz.com.bookshop.model.dto.response.user.UserForFrontDto;
import uz.com.bookshop.model.enums.Gender;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.model.enums.UserRole;
import uz.com.bookshop.repository.UserRepository;
import uz.com.bookshop.service.auth.JwtService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;




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
        UserForFrontDto userForFrontDto = modelMapper.map(userEntity, UserForFrontDto.class);
        JwtResponse jwtResponse = JwtResponse.builder()
                .userForFrontDto(userForFrontDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return StandardResponse.ok("Sign up successfully",jwtResponse);
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
            UserForFrontDto userForFrontDto =  modelMapper.map(userEntity, UserForFrontDto.class);
            JwtResponse jwtResponse = JwtResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(accessToken)
                    .userForFrontDto(userForFrontDto)
                    .build();
            return StandardResponse.ok("Sign in successfully",jwtResponse);
        }
        else {
            throw new UserBadRequestException("Something error during sign in!");
        }
    }







    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> currentUser = userRepository.findUserEntityByUsername(principal.getName());
        if (currentUser.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        user.get().setDeleted(true);
        user.get().setDeletedTime(LocalDateTime.now());
        user.get().setDeletedBy(currentUser.get().getId());
        userRepository.save(user.get());

        return StandardResponse.ok("User deleted!","DELETED");
    }







    @Override
    public StandardResponse<UserForFrontDto> getById(UUID id) {
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        UserForFrontDto userForFrontDto = modelMapper.map(user, UserForFrontDto.class);

        return StandardResponse.ok("This is user",userForFrontDto);
    }






    @Override
    public Page<UserForFrontDto> getAllUsers(Pageable pageable) {

        Page<UserEntity> userEntities = userRepository.findAllUsers(pageable);
        if (userEntities.isEmpty()){
            throw new DataNotFoundException("Users not found!");
        }

        return userEntities.map(userMapper::toDto);
    }







    @Override
    public StandardResponse<UserForFrontDto> assignToAdmin(UUID id) {
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        user.get().setRole(UserRole.ADMIN);
        UserEntity save = userRepository.save(user.get());
        UserForFrontDto userForFrontDto = modelMapper.map(save, UserForFrontDto.class);

        return StandardResponse.ok("Role changed!",userForFrontDto);
    }







    @Override
    public StandardResponse<UserForFrontDto> update(UserDto userDto, UUID id) {
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        UserEntity userEntity = user.get();
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(UserRole.USER);
        userEntity.setFullName(userDto.getFullName());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        userEntity.setAge(Period.between(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),LocalDate.now()).getYears());
        UserEntity save = userRepository.save(userEntity);

        UserForFrontDto userForFrontDto = modelMapper.map(save, UserForFrontDto.class);

        return StandardResponse.ok("User updated!",userForFrontDto);
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
