package edu.btec.bestore.service;

import edu.btec.bestore.domain.Category;
import edu.btec.bestore.exception.CategoryException;
import edu.btec.bestore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }
    public Category update(Long id, Category entity) {
        Optional<Category> existed = categoryRepository.findById(id);

        if (existed.isEmpty()){
            throw new CategoryException("Category id " + id + " does not existed");
        }
        try {
            Category existedCategory = existed.get();
            existedCategory.setName(entity.getName());
            existedCategory.setStatus(entity.getStatus());

            return categoryRepository.save(existedCategory);
        }catch (Exception ex){
            throw new CategoryException("Category is updated fail");
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findById(Long id) {
        Optional<Category> found = categoryRepository.findById(id);

        if (found.isEmpty()){
            throw new CategoryException("Category with id " + id + " does not exist");
        }

        return found.get();
    }

    public void deleteById(Long id){
        Category existed = findById(id);

        categoryRepository.delete(existed);
    }
}
