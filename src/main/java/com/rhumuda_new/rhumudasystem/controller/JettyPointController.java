package com.rhumuda_new.rhumudasystem.controller;

import com.rhumuda_new.rhumudasystem.entity.JettyPoint;
import com.rhumuda_new.rhumudasystem.repository.JettyPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/jettypoints")
public class JettyPointController {

    @Autowired
    private JettyPointRepository jettyPointRepository;

    @GetMapping
    public List<JettyPoint> getAllActiveJettyPoints() {
        return jettyPointRepository.findByIsActiveTrue();
    }
} 