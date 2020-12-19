package com.juan.gamedevforums.service;

import java.util.List;

import com.juan.gamedevforums.persistence.model.Categories;

public interface ICategoriesService {
    
    public List<Categories> findAll();
    
    public Categories findOne(Long id);
    
    public Categories findByName(String name);
    
    public Categories save(Categories categories);
    
    public void delete(Long id);
    
    public void delete(Categories categories);

}
