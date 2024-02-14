package com.brokerage.app.mapper;

import org.springframework.stereotype.Component;

import com.brokerage.app.dto.PropertyDTO;
import com.brokerage.app.entities.FlatType;
import com.brokerage.app.entities.Property;
import com.brokerage.app.entities.PropertyStatus;
import com.brokerage.app.entities.TenantType;
import com.brokerage.app.request.PropertyRequest;

@Component
public class PropertyMapper {

    public PropertyDTO toDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPropertyId(property.getPropertyId());
        propertyDTO.setPropertyImages(property.getPropertyImages());
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setAreaId(property.getArea());
        propertyDTO.setUserId(property.getUser());
        propertyDTO.setTenantType(property.getTenantType());
        propertyDTO.setFlatType(property.getFlatType());
        propertyDTO.setCarpetArea(property.getCarpet_area());
        propertyDTO.setStatus(property.getStatus());
        return propertyDTO;
    }

    public Property toEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setPropertyId(propertyDTO.getPropertyId());
        property.setPropertyImages(propertyDTO.getPropertyImages());
        property.setAddress(propertyDTO.getAddress());
        property.setArea(propertyDTO.getAreaId());
        property.setUser(propertyDTO.getUserId());
        property.setTenantType(propertyDTO.getTenantType());
        property.setCarpet_area(propertyDTO.getCarpetArea());
        property.setFlatType(propertyDTO.getFlatType());
        property.setStatus(propertyDTO.getStatus());
        return property;
    }
    
    public static Property mapRequestToEntity(PropertyRequest request) {
        Property property = new Property();
        property.setPropertyId(request.getPropertyId());
        property.setPropertyImages(request.getPropertyImages());
        property.setAddress(request.getAddress());
       property.setCarpet_area(request.getArea());
        property.setStatus(PropertyStatus.valueOf("AVAILABLE"));
        property.setTenantType(Enum.valueOf(TenantType.class, request.getTenantType()));
        property.setFlatType(Enum.valueOf(FlatType.class, request.getFlatType()));
        return property;
    }
    
    public static PropertyRequest mapEntityToRequest(Property entity) {
        PropertyRequest request = new PropertyRequest();
        request.setPropertyId(entity.getPropertyId());
        request.setPropertyImages(entity.getPropertyImages());
        request.setAddress(entity.getAddress());
        request.setAreaId(entity.getArea().getAreaId());
        request.setUserId(entity.getUser().getUserId());
        request.setStatus(entity.getStatus().toString());
        request.setArea(entity.getCarpet_area());
        request.setTenantType(entity.getTenantType().name());
        request.setFlatType(entity.getFlatType().name());
        return request;
    }
}

