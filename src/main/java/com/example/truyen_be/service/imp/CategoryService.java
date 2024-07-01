package com.example.truyen_be.service.imp;




import com.example.truyen_be.model.Category;
import com.example.truyen_be.repository.ICategoryRepository;
import com.example.truyen_be.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
@Autowired
private ICategoryRepository iCategoryRepository;


    @Override
    public Iterable<Category> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return iCategoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return iCategoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
iCategoryRepository.deleteById(id);
    }
}
