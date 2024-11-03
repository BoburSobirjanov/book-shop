package uz.com.bookshop.service.price;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.mapper.PriceMapper;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
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
    private final PriceMapper priceMapper;





    @Override
    public StandardResponse<PriceResponseDto> save(PriceDto priceDto, Principal principal) {
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
        PriceResponseDto priceResponseDto = modelMapper.map(save, PriceResponseDto.class);

        return StandardResponse.ok("Price added",priceResponseDto);

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

        return StandardResponse.ok("Price deleted","DELETED");
    }






    @Override
    public StandardResponse<PriceResponseDto> getById(UUID id) {
        Optional<Price> price = priceRepository.findPriceById(id);
        if (price.isEmpty()){
            throw new DataNotFoundException("Price not found!");
        }
        PriceResponseDto priceResponseDto = modelMapper.map(price, PriceResponseDto.class);

        return StandardResponse.ok("This is price",priceResponseDto);
    }






    @Override
    public StandardResponse<PriceResponseDto> update(PriceDto priceDto, UUID id) {
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
        PriceResponseDto priceResponseDto = modelMapper.map(save, PriceResponseDto.class);

        return StandardResponse.ok("Price updated",priceResponseDto);
    }






    @Override
    public Page<PriceResponseDto> getAll(Pageable pageable) {
        Page<Price> prices = priceRepository.findAllPrice(pageable);

        return prices.map(priceMapper::toDto);
    }
}
