package uz.com.bookshop.service.price;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.book.BookResponse;
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
import java.time.LocalDateTime;
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






    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<Price> price = priceRepository.findPriceById(id);
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        price.get().setDeleted(true);
        price.get().setDeletedBy(user.get().getId());
        price.get().setDeletedTime(LocalDateTime.now());
        priceRepository.save(price.get());

        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Price deleted!")
                .data("DELETED")
                .build();
    }






    @Override
    public StandardResponse<PriceResponse> getById(UUID id) {
        Optional<Price> price = priceRepository.findPriceById(id);
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }
        PriceResponse priceResponse = modelMapper.map(price,PriceResponse.class);

        return StandardResponse.<PriceResponse>builder()
                .status(Status.SUCCESS)
                .message("This is price")
                .data(priceResponse)
                .build();
    }






    @Override
    public StandardResponse<PriceResponse> update(PriceDto priceDto, UUID id) {
        Optional<Price> price = priceRepository.findPriceById(id);
        Optional<Book> book = bookRepository.findBookById(UUID.fromString(priceDto.getBookId()));
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }
        price.get().setPrice(priceDto.getPrice());

        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        price.get().setCreatedBy(price.get().getCreatedBy());
        price.get().setBook(book.get());
        Price save = priceRepository.save(price.get());
        PriceResponse priceResponse = modelMapper.map(save,PriceResponse.class);

        return StandardResponse.<PriceResponse>builder()
                .status(Status.SUCCESS)
                .message("Price updated!")
                .data(priceResponse)
                .build();
    }






    @Override
    public Page<PriceResponse> getAll(Pageable pageable) {
        Page<Price> prices = priceRepository.findAllPrice(pageable);

        return prices.map(price -> new PriceResponse(price.getId(), price.getPrice(),
                modelMapper.map(price.getBook(), BookResponse.class)));
    }
}
