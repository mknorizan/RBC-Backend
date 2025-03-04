package com.rhumuda_new.rhumudasystem.controller;

import com.rhumuda_new.rhumudasystem.entity.Package;
import com.rhumuda_new.rhumudasystem.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping("/category/{categoryId}")
    public List<Package> getPackagesByCategory(@PathVariable Long categoryId) {
        System.out.println("Received request for category: " + categoryId);
        List<Package> packages = packageRepository.findByCategoryIdAndIsActiveTrue(categoryId);
        System.out.println("Found " + packages.size() + " packages for category " + categoryId);
        packages.forEach(p -> {
            System.out.println("Package details:");
            System.out.println("  ID: " + p.getId());
            System.out.println("  Name: " + p.getName());
            System.out.println("  Category: " + p.getCategoryId());
            System.out.println("  Active: " + p.getIsActive());
        });
        return packages;
    }
} 