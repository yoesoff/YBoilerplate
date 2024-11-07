package com.mhyusuf.yboilerplate.service;

import com.mhyusuf.yboilerplate.entity.DataObject;
import com.mhyusuf.yboilerplate.repository.DataObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisSpringDataService {

    private final DataObjectRepository dataObjectRepository;

    @Autowired
    public RedisSpringDataService(DataObjectRepository dataObjectRepository) {
        this.dataObjectRepository = dataObjectRepository;
    }

    public void saveData(String key, String value) {
        dataObjectRepository.save(new DataObject(key, value));
    }

    public String getData(String key) {
        return dataObjectRepository.findById(key)
                .map(DataObject::getValue)
                .orElse(null);
    }

    public boolean deleteData(String key) {
        if (dataObjectRepository.existsById(key)) {
            dataObjectRepository.deleteById(key);
            return true;
        }
        return false;
    }

    public Iterable<DataObject> getAllData() {
        return dataObjectRepository.findAll();
    }
}

