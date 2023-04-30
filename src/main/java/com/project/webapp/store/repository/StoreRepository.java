package com.project.webapp.store.repository;

import com.project.webapp.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  StoreRepository extends JpaRepository<Store, Integer> {

}