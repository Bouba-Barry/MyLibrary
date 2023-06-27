package com.bouba.mylibrary.category;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface ICategoryService {
    public List<Category> allCategory();
    public Category saveCategory(Category cat);
    public void deleteCategory(String code);


}
