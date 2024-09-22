package dev.earlspilner.loans.feign;

import dev.earlspilner.loans.dto.BookRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        name = "library-service",
        url = "http://localhost:9094/api",
        configuration = FeignConfig.class
)
public interface LibraryClient {

    @GetMapping("/library/{bookId}")
    BookRecordDto getBookRecord(@PathVariable Integer bookId);

    @PutMapping("/library/{bookId}")
    void setBookStatus(@PathVariable Integer bookId, @RequestBody BookRecordDto bookRecordDto);

}
