package com.rhumuda.charterservice.controller;

import com.rhumuda.charterservice.dto.PackageDetails;
import com.rhumuda.charterservice.model.Package;
import com.rhumuda.charterservice.model.BoatPackage;
import com.rhumuda.charterservice.model.FishingPackage;
import com.rhumuda.charterservice.repository.PackageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {
    private static final Logger logger = LoggerFactory.getLogger(PackageController.class);
    private final PackageRepository packageRepository;

    @GetMapping
    public ResponseEntity<List<PackageDetails>> getAllPackages() {
        try {
            List<PackageDetails> packages = packageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            logger.info("Retrieved {} packages", packages.size());
            return ResponseEntity.ok(packages);
        } catch (Exception e) {
            logger.error("Error retrieving packages", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public PackageDetails getPackage(@PathVariable Long id) {
        Package pkg = packageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Package not found"));
        return convertToDTO(pkg);
    }

    private PackageDetails convertToDTO(Package pkg) {
        PackageDetails dto = new PackageDetails();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setDescription(pkg.getDescription());
        dto.setType(pkg.getPackageType());
        dto.setPrice(pkg.getPrice());
        dto.setServices(pkg.getServices());

        if (pkg instanceof BoatPackage) {
            BoatPackage boatPkg = (BoatPackage) pkg;
            dto.setAdultPrice(boatPkg.getAdultPrice());
            dto.setKidPrice(boatPkg.getKidPrice());
            dto.setPrivateBoatPrice(boatPkg.getPrivateBoatPrice());
        } else if (pkg instanceof FishingPackage) {
            FishingPackage fishingPkg = (FishingPackage) pkg;
            dto.setPriceMin(fishingPkg.getPriceMin());
            dto.setPriceMax(fishingPkg.getPriceMax());
            dto.setDistance(fishingPkg.getDistance());
            dto.setTechniques(fishingPkg.getTechniques());
        }

        return dto;
    }
} 