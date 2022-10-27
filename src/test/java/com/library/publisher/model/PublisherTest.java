package com.library.publisher.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PublisherTest {

    @Test
    void testConstructor() {
        Publisher actualPublisher = new Publisher();
        actualPublisher.setPublisherId(123L);
        actualPublisher.setPublisherName(" hari");
        actualPublisher.setPublishingYear(2020L);
        assertEquals(123L, actualPublisher.getPublisherId());
        assertEquals(" hari", actualPublisher.getPublisherName());
        assertEquals(2020L, actualPublisher.getPublishingYear());
    }


    @Test
    void testConstructor2() {
        Publisher actualPublisher = new Publisher(123L, " hari", 2020L);

        assertEquals(123L, actualPublisher.getPublisherId());
        assertEquals(2020L, actualPublisher.getPublishingYear());
        assertEquals(" hari", actualPublisher.getPublisherName());
    }
}

