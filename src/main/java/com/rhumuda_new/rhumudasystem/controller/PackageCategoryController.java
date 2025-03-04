package com.rhumuda_new.rhumudasystem.controller;

import com.rhumuda_new.rhumudasystem.entity.PackageCategory;
import com.rhumuda_new.rhumudasystem.repository.PackageCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/package-categories")
public class PackageCategoryController {

    @Autowired
    private PackageCategoryRepository packageCategoryRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PackageCategory> getCategoryById(@PathVariable Long id) {
        return packageCategoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 