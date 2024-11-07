package com.mhyusuf.yboilerplate.repository;

import com.mhyusuf.yboilerplate.entity.DataObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataObjectRepository extends CrudRepository<DataObject, String> {
    // Tidak perlu menambahkan metode apapun, cukup menggunakan metode CRUD dasar
}
