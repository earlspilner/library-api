package dev.earlspilner.loan.feign;

import dev.earlspilner.loan.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        name = "books-service",
        url = "http://localhost:9092/api/books",
        configuration = FeignConfig.class
)
public interface BookClient {

    @GetMapping("/{bookId}")
    BookDto getBook(@PathVariable Integer bookId);

    @PutMapping("/{bookId}")
    BookDto updateBook(@PathVariable Integer bookId, @RequestBody BookDto bookDto);

}
