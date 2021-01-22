package com.juan.gamedevforums.persistence.dao;

import com.juan.gamedevforums.persistence.model.Categories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Categories findByName(String name);
    Categories findByNameIgnoreCase(String name);

    @Override
    void delete(Categories categories);

}
