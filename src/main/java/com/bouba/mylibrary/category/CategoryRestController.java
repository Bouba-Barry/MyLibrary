package com.bouba.mylibrary.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategory(){
        return new ResponseEntity<>(categoryService.allCategory(), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/newCategory")
    public ResponseEntity<Category> addNewCategory(Category cat){
        return new ResponseEntity<>(categoryService.saveCategory(cat), HttpStatusCode.valueOf(200));
    }
}
