package com.epam.microservices.shop.feature;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CucumberOrderContextHolder {

    private IncomingLogisticalOrderDto incomingLogisticalOrderDto;
    private LogisticalOrder logisticalOrder;
    private String generatedOrderIdentifier;

    public IncomingLogisticalOrderDto getIncomingLogisticalOrderDto() {
        return incomingLogisticalOrderDto;
    }

    public String getGeneratedOrderIdentifier() {
        return generatedOrderIdentifier;
    }

    public void setGeneratedOrderIdentifier(String generatedOrderIdentifier) {
        this.generatedOrderIdentifier = generatedOrderIdentifier;
    }

    public void setIncomingLogisticalOrderDto(IncomingLogisticalOrderDto incomingLogisticalOrderDto) {
        this.incomingLogisticalOrderDto = incomingLogisticalOrderDto;
    }

    public void setLogisticalOrder(LogisticalOrder logisticalOrder) {
        this.logisticalOrder = logisticalOrder;
    }

    public LogisticalOrder getLogisticalOrder() {
        return logisticalOrder;
    }
}
