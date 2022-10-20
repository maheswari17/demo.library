package com.library.publisher.service;

import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.exceptions.CustomNotFoundException.PublisherNotFoundException;
import com.library.publisher.dto.PublisherDto;
import com.library.publisher.model.Publisher;
import com.library.publisher.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {
    private PublisherRepository publisherRepository;
    private ModelMapper modelMapper;

    public PublisherServiceImpl(PublisherRepository publisherRepository,ModelMapper modelMapper) {
        this.publisherRepository = publisherRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream().map(this::buildPublisherDto).collect(Collectors.toList());
    }

    @Override
    public PublisherDto savePublisher(PublisherDto publisherDto) {
        Publisher publisher= publisherRepository.save(buildPublisher(publisherDto));
        return buildPublisherDto(publisher);
    }

    @Override
    public PublisherDto getPublisher(long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if(publisher.isPresent()) {
            return buildPublisherDto(publisher.get());
        }throw new PublisherNotFoundException("publisher details not found");
    }

    @Override
    public void deletePublisher(long id) {
        try {
            publisherRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new MemberNotFoundException("publisher with id:" + id + " does not exist");
        }
    }

    public PublisherDto buildPublisherDto(Publisher publisher)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        PublisherDto publisherDto = modelMapper.map(publisher,PublisherDto.class);
        return publisherDto;
    }

    public Publisher buildPublisher(PublisherDto publisherDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Publisher publisher = modelMapper.map(publisherDto,Publisher.class);
        return publisher;
    }
}