package com.qa.ims.persistence.dao;

import java.util.List;

public interface Dao<T> {
	
	T read(Long id);

    List<T> readAll();
     
    T create(T t);
     
    T update(T t);
     
    void delete(long id);
}
	