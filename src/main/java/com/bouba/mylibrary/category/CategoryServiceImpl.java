package com.bouba.mylibrary.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategorieDao categorieDao;

    @Override
    public List<Category> allCategory() {
        return categorieDao.findAll();
    }

    @Override
    public Category saveCategory(Category cat) {
        return categorieDao.save(cat);
    }


    @Override
    public void deleteCategory(String code) {
        categorieDao.deleteById(code);
    }
}
