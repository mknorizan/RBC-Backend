package com.rhumuda_new.rhumudasystem.controller;

import com.rhumuda_new.rhumudasystem.entity.AddOn;
import com.rhumuda_new.rhumudasystem.repository.AddOnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@RequestMapping("/api/addons")
@CrossOrigin(origins = "http://localhost:3000")
public class AddOnController {

    @Autowired
    private AddOnRepository addOnRepository;

    @GetMapping
    public List<AddOn> getAllActiveAddOns() {
        return addOnRepository.findByIsActiveTrue();
    }
}
