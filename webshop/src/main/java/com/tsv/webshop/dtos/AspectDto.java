package com.tsv.webshop.dtos;

import lombok.*;

@Getter
@NoArgsConstructor

public class AspectDto {
    private String serviceName;

    private Long duration;
     public void setDuration(Long time){
         duration+=time;
     }

    public AspectDto(String serviceName) {
        this.serviceName = serviceName;
        this.duration = 0L;
    }

    @Override
    public String toString() {
        return "serviceName = " + getServiceName() +  " "
                + " duration = " + getDuration()
                + " ms" + System.lineSeparator();
    }
}
