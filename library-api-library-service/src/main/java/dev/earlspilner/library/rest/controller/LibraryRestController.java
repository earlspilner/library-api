package dev.earlspilner.library.rest.controller;

import dev.earlspilner.library.dto.BookRecordDto;
import dev.earlspilner.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alexander Dudkin
 */
@RestController
@RequestMapping("/library")
public class LibraryRestController implements LibraryApi {

    private final LibraryService libraryService;

    @Autowired
    public LibraryRestController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    @PostMapping
    public ResponseEntity<BookRecordDto> addBootRecord(@RequestBody BookRecordDto dto) {
        return new ResponseEntity<>(libraryService.addBookRecord(dto), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{bookId}")
    public ResponseEntity<BookRecordDto> getBookRecord(@PathVariable Integer bookId) {
        return new ResponseEntity<>(libraryService.getBookRecord(bookId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookRecordDto> updateBookRecord(@PathVariable Integer bookId,
                                                          @RequestBody BookRecordDto dto) {
        return new ResponseEntity<>(libraryService.updateBookRecord(bookId, dto), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookRecord(@PathVariable Integer bookId) {
        libraryService.deleteBookRecord(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
