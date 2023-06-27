package com.bouba.mylibrary.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICategorieDao extends JpaRepository<Category, String> {
}
