package com.library.publisher.controller;
import com.library.publisher.dto.PublisherDto;
import com.library.publisher.service.PublisherServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

   private PublisherServiceImpl publisherServiceImpl;
    public PublisherController(PublisherServiceImpl publisherServiceImpl) {
        this.publisherServiceImpl = publisherServiceImpl;
    }

    @GetMapping
    public List<PublisherDto> getAllPublishers() {
        return publisherServiceImpl.getAllPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> get(@PathVariable Integer id) {
        return new ResponseEntity<>(publisherServiceImpl.getPublisher(id), HttpStatus.OK);
    }

    @PostMapping
    public PublisherDto add(@RequestBody PublisherDto publisherDto) {
        return publisherServiceImpl.savePublisher(publisherDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "publisher deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        publisherServiceImpl.deletePublisher(id);
    }
}