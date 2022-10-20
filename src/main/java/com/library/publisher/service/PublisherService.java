package com.library.publisher.service;
import com.library.publisher.dto.PublisherDto;
import java.util.List;

public interface PublisherService {
    List<PublisherDto> getAllPublishers();
    PublisherDto savePublisher(PublisherDto publisherDto);
    PublisherDto getPublisher(long id);
    void deletePublisher(long id);
}