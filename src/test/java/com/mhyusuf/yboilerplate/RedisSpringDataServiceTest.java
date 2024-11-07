package com.mhyusuf.yboilerplate;


import com.mhyusuf.yboilerplate.entity.DataObject;
import com.mhyusuf.yboilerplate.repository.DataObjectRepository;
import com.mhyusuf.yboilerplate.service.RedisSpringDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.stream.StreamSupport;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisSpringDataServiceTest {

    @Mock
    private DataObjectRepository dataObjectRepository;

    @InjectMocks
    private RedisSpringDataService redisSpringDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveData() {
        // Arrange
        ArgumentCaptor<DataObject> captor = ArgumentCaptor.forClass(DataObject.class);
        DataObject expectedDataObject = new DataObject("testKey", "testValue");

        // Act
        redisSpringDataService.saveData("testKey", "testValue");

        // Assert
        verify(dataObjectRepository, times(1)).save(captor.capture());
        DataObject actualDataObject = captor.getValue();

        assertEquals(expectedDataObject.getId(), actualDataObject.getId());
        assertEquals(expectedDataObject.getValue(), actualDataObject.getValue());
    }


    @Test
    void testGetData() {
        String key = "testKey";
        DataObject dataObject = new DataObject(key, "testValue");
        when(dataObjectRepository.findById(key)).thenReturn(Optional.of(dataObject));

        String result = redisSpringDataService.getData(key);

        assertNotNull(result);
        assertEquals("testValue", result);
        verify(dataObjectRepository, times(1)).findById(key);
    }

    @Test
    void testGetDataNotFound() {
        String key = "nonExistentKey";
        when(dataObjectRepository.findById(key)).thenReturn(Optional.empty());

        String result = redisSpringDataService.getData(key);

        assertNull(result);
        verify(dataObjectRepository, times(1)).findById(key);
    }

    @Test
    void testDeleteData() {
        String key = "testKey";
        when(dataObjectRepository.existsById(key)).thenReturn(true);

        boolean isDeleted = redisSpringDataService.deleteData(key);

        assertTrue(isDeleted);
        verify(dataObjectRepository, times(1)).deleteById(key);
    }

    @Test
    void testDeleteDataNotFound() {
        String key = "nonExistentKey";
        when(dataObjectRepository.existsById(key)).thenReturn(false);

        boolean isDeleted = redisSpringDataService.deleteData(key);

        assertFalse(isDeleted);
        verify(dataObjectRepository, times(0)).deleteById(key);
    }

    @Test
    void testGetAllData() {
        Iterable<DataObject> dataObjects = List.of(
                new DataObject("key1", "value1"),
                new DataObject("key2", "value2")
        );

        when(dataObjectRepository.findAll()).thenReturn(dataObjects);

        Iterable<DataObject> result = redisSpringDataService.getAllData();

        assertNotNull(result);
        assertEquals(2, StreamSupport.stream(result.spliterator(), false).count());
        verify(dataObjectRepository, times(1)).findAll();
    }
}
