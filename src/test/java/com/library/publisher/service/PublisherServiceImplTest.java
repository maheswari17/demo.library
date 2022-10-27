package com.library.publisher.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.exceptions.CustomNotFoundException.PublisherNotFoundException;
import com.library.publisher.dto.PublisherDto;
import com.library.publisher.model.Publisher;
import com.library.publisher.repository.PublisherRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PublisherServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PublisherServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherServiceImpl publisherServiceImpl;

    @Test
    void testGetAllPublishers() {
        when(this.publisherRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.publisherServiceImpl.getAllPublishers().isEmpty());
        verify(this.publisherRepository).findAll();
    }

    @Test
    void testGetAllPublishers3() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);

        ArrayList<Publisher> publisherList = new ArrayList<>();
        publisherList.add(publisher);
        when(this.publisherRepository.findAll()).thenReturn(publisherList);
        when(this.modelMapper.map((Object) any(), (Class<PublisherDto>) any()))
                .thenThrow(new PublisherNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.getAllPublishers());
        verify(this.publisherRepository).findAll();
        verify(this.modelMapper).map((Object) any(), (Class<PublisherDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testSavePublisher() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        when(this.publisherRepository.save((Publisher) any())).thenReturn(publisher);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new PublisherNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" ravi");
        publisherDto.setPublishingYear(2020L);
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.savePublisher(publisherDto));
        verify(this.modelMapper).map((Object) any(), (Class<Publisher>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testSavePublisher2() {
        Publisher publisher = mock(Publisher.class);
        doNothing().when(publisher).setPublisherId(anyLong());
        doNothing().when(publisher).setPublisherName((String) any());
        doNothing().when(publisher).setPublishingYear(anyLong());
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        when(this.publisherRepository.save((Publisher) any())).thenReturn(publisher);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(null);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" ravi");
        publisherDto.setPublishingYear(2020L);
        assertNull(this.publisherServiceImpl.savePublisher(publisherDto));
        verify(this.publisherRepository).save((Publisher) any());
        verify(publisher).setPublisherId(anyLong());
        verify(publisher).setPublisherName((String) any());
        verify(publisher).setPublishingYear(anyLong());
        verify(this.modelMapper, atLeast(1)).map((Object) any(), (Class<Publisher>) any());
        verify(this.modelMapper, atLeast(1)).getConfiguration();
    }


    @Test
    void testGetPublisher() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        Optional<Publisher> ofResult = Optional.of(publisher);
        when(this.publisherRepository.findById((Long) any())).thenReturn(ofResult);

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" ravi");
        publisherDto.setPublishingYear(2020L);
        when(this.modelMapper.map((Object) any(), (Class<PublisherDto>) any())).thenReturn(publisherDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertSame(publisherDto, this.publisherServiceImpl.getPublisher(123L));
        verify(this.publisherRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<PublisherDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testGetPublisher2() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        Optional<Publisher> ofResult = Optional.of(publisher);
        when(this.publisherRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.modelMapper.map((Object) any(), (Class<PublisherDto>) any()))
                .thenThrow(new PublisherNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.getPublisher(123L));
        verify(this.publisherRepository).findById((Long) any());
        verify(this.modelMapper).map((Object) any(), (Class<PublisherDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testDeletePublisher() {
        doNothing().when(this.publisherRepository).deleteById((Long) any());
        this.publisherServiceImpl.deletePublisher(123L);
        verify(this.publisherRepository).deleteById((Long) any());
        assertTrue(this.publisherServiceImpl.getAllPublishers().isEmpty());
    }


    @Test
    void testDeletePublisher2() {
        doThrow(new PublisherNotFoundException("Exception")).when(this.publisherRepository).deleteById((Long) any());
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.deletePublisher(123L));
        verify(this.publisherRepository).deleteById((Long) any());
    }

    @Test
    void testBuildPublisherDto() {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" ravi");
        publisherDto.setPublishingYear(2020L);
        when(this.modelMapper.map((Object) any(), (Class<PublisherDto>) any())).thenReturn(publisherDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        assertSame(publisherDto, this.publisherServiceImpl.buildPublisherDto(publisher));
        verify(this.modelMapper).map((Object) any(), (Class<PublisherDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildPublisherDto2() {
        when(this.modelMapper.map((Object) any(), (Class<PublisherDto>) any()))
                .thenThrow(new PublisherNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName("ravi ");
        publisher.setPublishingYear(2020L);
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.buildPublisherDto(publisher));
        verify(this.modelMapper).map((Object) any(), (Class<PublisherDto>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testBuildPublisher() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(123L);
        publisher.setPublisherName(" ravi");
        publisher.setPublishingYear(2020L);
        when(this.modelMapper.map((Object) any(), (Class<Publisher>) any())).thenReturn(publisher);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName(" ravi");
        publisherDto.setPublishingYear(2020L);
        assertSame(publisher, this.publisherServiceImpl.buildPublisher(publisherDto));
        verify(this.modelMapper).map((Object) any(), (Class<Publisher>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildPublisher2() {
        when(this.modelMapper.map((Object) any(), (Class<Publisher>) any()))
                .thenThrow(new PublisherNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherId(123L);
        publisherDto.setPublisherName("Publisher Name");
        publisherDto.setPublishingYear(1L);
        assertThrows(PublisherNotFoundException.class, () -> this.publisherServiceImpl.buildPublisher(publisherDto));
        verify(this.modelMapper).map((Object) any(), (Class<Publisher>) any());
        verify(this.modelMapper).getConfiguration();
    }

}

