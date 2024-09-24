package dev.earlspilner.loans.feign;

import dev.earlspilner.loans.dto.BookRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        name = "library-service",
        configuration = FeignConfig.class
)
public interface LibraryClient {

    @GetMapping("/api/library/{bookId}")
    BookRecordDto getBookRecord(@PathVariable Integer bookId);

    @PutMapping("/api/library/{bookId}")
    void setBookStatus(@PathVariable Integer bookId, @RequestBody BookRecordDto bookRecordDto);

}
