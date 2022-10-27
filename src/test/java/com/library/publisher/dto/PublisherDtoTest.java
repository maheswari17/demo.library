package com.library.publisher.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PublisherDtoTest {

    @Test
    void testConstructor() {
        PublisherDto actualPublisherDto = new PublisherDto();
        actualPublisherDto.setPublisherId(123L);
        actualPublisherDto.setPublisherName(" hari");
        actualPublisherDto.setPublishingYear(2020L);
        assertEquals(123L, actualPublisherDto.getPublisherId());
        assertEquals(" hari", actualPublisherDto.getPublisherName());
        assertEquals(2020L, actualPublisherDto.getPublishingYear());
    }
}

