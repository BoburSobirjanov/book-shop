package uz.com.bookshop.service.userpurchases;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.mapper.UserPurchasesMapper;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponseDto;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.price.Price;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.model.entity.user.UserPurchases;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.PriceRepository;
import uz.com.bookshop.repository.UserPurchasesRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPurchasesServiceImpl implements UserPurchasesService{




    private final UserPurchasesRepository userPurchasesRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final UserPurchasesMapper userPurchasesMapper;




    @Override
    public StandardResponse<UserPurchasesResponseDto> save(UserPurchasesDto userPurchasesDto, Principal principal) {
        Optional<Book> book = bookRepository.findBookById(UUID.fromString(userPurchasesDto.getBook()));
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        Optional<Price> price = priceRepository.findPriceByBookId(book.get());
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }

        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }

        UserPurchases userPurchases = modelMapper.map(userPurchasesDto, UserPurchases.class);
        userPurchases.setBook(book.get());
        userPurchases.setPrice(price.get().getPrice());
        userPurchases.setUser(user.get());
        UserPurchases save = userPurchasesRepository.save(userPurchases);

        UserPurchasesResponseDto userPurchasesResponseDto = modelMapper.map(save, UserPurchasesResponseDto.class);

        return StandardResponse.ok("User purchases created!",userPurchasesResponseDto);
    }






    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<UserPurchases> userPurchases = userPurchasesRepository.findUserPurchasesById(id);
        if (userPurchases.isEmpty()){
            throw new DataNotFoundException("User purchases not found!");
        }
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        userPurchases.get().setDeleted(true);
        userPurchases.get().setDeletedBy(user.get().getId());
        userPurchases.get().setDeletedTime(LocalDateTime.now());

        userPurchasesRepository.save(userPurchases.get());

        return StandardResponse.ok("User purchase deleted","DELETED");
    }






    @Override
    public StandardResponse<UserPurchasesResponseDto> getById(UUID id) {

        Optional<UserPurchases> userPurchases = userPurchasesRepository.findUserPurchasesById(id);
        if (userPurchases.isEmpty()){
            throw new DataNotFoundException("User purchases not found!");
        }
        UserPurchasesResponseDto userPurchasesResponseDto = modelMapper.map(userPurchases, UserPurchasesResponseDto.class);

        return StandardResponse.ok("User purchases created!",userPurchasesResponseDto);
    }






    @Override
    public StandardResponse<UserPurchasesResponseDto> update(UUID id, UserPurchasesDto userPurchasesDto) {
        Optional<Book> book = bookRepository.findBookById(UUID.fromString(userPurchasesDto.getBook()));
        Optional<UserPurchases> userPurchases = userPurchasesRepository.findUserPurchasesById(id);
        if (userPurchases.isEmpty()){
            throw new DataNotFoundException("User purchases not found!");
        }
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        Optional<Price> price = priceRepository.findPriceByBookId(book.get());
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }
        userPurchases.get().setBook(book.get());
        userPurchases.get().setPrice(price.get().getPrice());
        userPurchases.get().setUser(userPurchases.get().getUser());
        UserPurchases save = userPurchasesRepository.save(userPurchases.get());

        UserPurchasesResponseDto userPurchasesResponseDto = modelMapper.map(save, UserPurchasesResponseDto.class);

        return StandardResponse.ok("User purchases updated!",userPurchasesResponseDto);
    }






    @Override
    public Page<UserPurchasesResponseDto> getAllUserPurchases(Pageable pageable) {
        Page<UserPurchases> userPurchases = userPurchasesRepository.findAllUserPurchases(pageable);

        return userPurchases.map(userPurchasesMapper::toDto);
    }






    @Override
    public Page<UserPurchasesResponseDto> getUserIdPurchases(Pageable pageable, UUID id) {
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        Page<UserPurchases> userPurchases = userPurchasesRepository.findUserPurchasesByUser(user.get(),pageable);

        return userPurchases.map(userPurchasesMapper::toDto);
    }






    @Override
    public Page<UserPurchasesResponseDto> getBookIdPurchases(Pageable pageable, UUID id) {
        Optional<Book> book = bookRepository.findBookById(id);
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        Page<UserPurchases> userPurchases = userPurchasesRepository.findUserPurchasesByBook(book.get(),pageable);

        return userPurchases.map(userPurchasesMapper::toDto);
    }
}