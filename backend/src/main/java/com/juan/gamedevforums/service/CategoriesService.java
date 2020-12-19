package com.juan.gamedevforums.service;

import javax.transaction.Transactional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.juan.gamedevforums.persistence.model.Categories;
import com.juan.gamedevforums.persistence.dao.CategoriesRepository;

@Service
@Transactional
public class CategoriesService implements ICategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories findOne(Long id) {
        // todo fix optional
        return categoriesRepository.findById(id).get();
    }

    @Override
    public Categories findByName(String name) {
        return categoriesRepository.findByName(name);
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public void delete(Long id) {
        delete(findOne(id));
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepository.delete(categories);
    }

}

