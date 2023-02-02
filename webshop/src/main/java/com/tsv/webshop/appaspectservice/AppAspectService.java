package com.tsv.webshop.appaspectservice;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tsv.webshop.dtos.AspectDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Aspect
@Component
public class AppAspectService {

    private List<AspectDto> aspectDtoList;

    public AppAspectService() {
        aspectDtoList = new ArrayList<>();
        aspectDtoList.add(new AspectDto("webshop.services.CartService"));
        aspectDtoList.add(new AspectDto("webshop.services.ProductService"));
    }

    @Around("execution(public * com.tsv.webshop.services.CartService.*(..))")
    public Object cartServiseTimeFixing(ProceedingJoinPoint proceedingJoinPoint)throws Throwable {

        Long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Long end = System.currentTimeMillis();
        Long duration = end - begin;
        for(AspectDto dto: aspectDtoList){
            if(dto.getServiceName().equals("webshop.services.CartService"))
                dto.setDuration(duration);
        }


        return result;
    }

    @Around("execution(public * com.tsv.webshop.services.ProductService.*(..))")
    public Object productServiseTimeFixing(ProceedingJoinPoint proceedingJoinPoint)throws Throwable {

        Long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Long end = System.currentTimeMillis();
        Long duration = end - begin;
        for(AspectDto dto: aspectDtoList){
            if(dto.getServiceName().equals("webshop.services.ProductService"))
                dto.setDuration(duration);
        }


        return result;
    }


    public List<AspectDto> getTimeAspect() {
        return aspectDtoList;
    }
}



