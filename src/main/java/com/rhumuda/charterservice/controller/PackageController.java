package com.rhumuda.charterservice.controller;

import com.rhumuda.charterservice.dto.PackageDTO;
import com.rhumuda.charterservice.model.Package;
import com.rhumuda.charterservice.model.BoatPackage;
import com.rhumuda.charterservice.model.FishingPackage;
import com.rhumuda.charterservice.repository.PackageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {
    private final PackageRepository packageRepository;

    @GetMapping
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        List<PackageDTO> packages = packageRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageDTO> getPackage(@PathVariable String packageId) {
        return packageRepository.findByPackageId(packageId)
            .map(this::convertToDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }

    private PackageDTO convertToDTO(Package pkg) {
        PackageDTO dto = new PackageDTO();
        dto.setPackageId(pkg.getPackageId());
        dto.setTitle(pkg.getTitle());
        dto.setDescription(pkg.getDescription());
        dto.setDuration(pkg.getDuration());
        dto.setCapacity(pkg.getCapacity());
        dto.setServices(pkg.getServices());
        
        if (pkg instanceof BoatPackage) {
            dto.setPackageType("BOAT");
            BoatPackage boatPkg = (BoatPackage) pkg;
            dto.setAdultPrice(boatPkg.getAdultPrice());
            dto.setKidPrice(boatPkg.getKidPrice());
            dto.setPrivateBoatPrice(boatPkg.getPrivateBoatPrice());
        } else if (pkg instanceof FishingPackage) {
            dto.setPackageType("FISHING");
            FishingPackage fishingPkg = (FishingPackage) pkg;
            dto.setPriceMin(fishingPkg.getPriceMin());
            dto.setPriceMax(fishingPkg.getPriceMax());
            dto.setDistanceRange(fishingPkg.getDistance());
            dto.setTechniques(fishingPkg.getTechniques());
        }
        
        return dto;
    }
} 