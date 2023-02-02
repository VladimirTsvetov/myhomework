package com.tsv.webshop.controllers;

import com.tsv.webshop.appaspectservice.AppAspectService;
import com.tsv.webshop.dtos.AspectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aspect")
public class AppAspectController {
    private final AppAspectService appAspectService;
    @GetMapping
    public String getTimeAspect(){
        List<AspectDto> aspectDtoList = appAspectService.getTimeAspect();
        StringBuilder sb = new StringBuilder();
        for(AspectDto dto : aspectDtoList){
            sb.append(dto.toString());
        }
        return sb.toString();
    }
}
