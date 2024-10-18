package uz.com.bookshop.service.price;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.price.Price;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.PriceRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{

    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;


    @Override
    public StandardResponse<PriceResponse> save(PriceDto priceDto, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<Book> book = bookRepository.findBookById(UUID.fromString(priceDto.getBookId()));
        Price price = modelMapper.map(priceDto, Price.class);
        price.setPrice(priceDto.getPrice());

        if (user.isEmpty() || book.isEmpty()){
              throw new DataNotFoundException("Book not found!");
        }
        price.setCreatedBy(user.get().getId());
        price.setBook(book.get());
        Price save = priceRepository.save(price);
        PriceResponse priceResponse = modelMapper.map(save,PriceResponse.class);

        return StandardResponse.<PriceResponse>builder()
                .status(Status.SUCCESS)
                .message("Price added!")
                .data(priceResponse)
                .build();

    }
}
