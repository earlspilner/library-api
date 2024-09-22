package dev.earlspilner.books.feign;

import dev.earlspilner.books.dto.BookRecordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Alexander Dudkin
 */
@FeignClient(
        name = "library-service",
        url = "http://localhost:9094/api/library",
        configuration = FeignConfig.class
)
public interface LibraryClient {

    @PostMapping
    void addBookRecord(@RequestBody BookRecordDto dto);

    @DeleteMapping("/{bookId}")
    void deleteBookRecord(@PathVariable Integer bookId);

}
