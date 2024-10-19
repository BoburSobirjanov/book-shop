package uz.com.bookshop.service.userpurchases;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponse;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.price.Price;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.model.entity.user.UserPurchases;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.PriceRepository;
import uz.com.bookshop.repository.UserPurchasesRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
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


    @Override
    public StandardResponse<UserPurchasesResponse> save(UserPurchasesDto userPurchasesDto, Principal principal) {
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

        UserPurchasesResponse userPurchasesResponse = modelMapper.map(save,UserPurchasesResponse.class);

        return StandardResponse.<UserPurchasesResponse>builder()
                .status(Status.SUCCESS)
                .message("User purchases created!")
                .data(userPurchasesResponse)
                .build();
    }
}
