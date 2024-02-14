package com.brokerage.app.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brokerage.app.dto.PropertyDTO;
import com.brokerage.app.entities.Property;
import com.brokerage.app.mapper.PropertyMapper;
import com.brokerage.app.repository.AreaRepository;
import com.brokerage.app.repository.PropertyRepository;
import com.brokerage.app.repository.UserRepository;
import com.brokerage.app.request.PropertyRequest;


@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    
    @Autowired 
    private AreaRepository areaRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        properties.forEach(property-> property.getArea().getCity());
        return properties.stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO getPropertyById(Integer id) {
        Property propertyOptional = propertyRepository.findById(id).orElseThrow();
       
            return propertyMapper.toDTO(propertyOptional);
        
    }

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        Property property = propertyMapper.toEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return propertyMapper.toDTO(savedProperty);
    }

    @Override
    public void deleteProperty(Integer id) {
        propertyRepository.deleteById(id);
    }
    
    @Override
    public PropertyDTO addProperty(PropertyRequest propertyRequest, List<MultipartFile> images) {
        // Validate or process images as needed
        // You may want to save images to a storage service (AWS S3, Google Cloud Storage, etc.) and store the URLs in the database

        Property property =propertyMapper.mapRequestToEntity(propertyRequest);
        
        property.setArea(areaRepository.findById(propertyRequest.getAreaId()).orElseThrow());
        property.setUser(userRepository.findById(propertyRequest.getUserId()).orElseThrow() );
        if(images!=null) {
        // Set images in the property entity based on your data model
        property.setPropertyImages(processImages(images));
        }
        property.setPropertyImages(null);
        
        Property savedProperty = propertyRepository.save(property);
        System.out.println("saved"+savedProperty);
        return propertyMapper.toDTO(savedProperty);
    }

    private List<byte[]> processImages(List<MultipartFile> images) {
        // Implement logic to process and save images, e.g., convert to byte arrays
        // You may use a library like Apache Commons IO to convert MultipartFile to byte array
        // Be aware of potential security issues, validate and sanitize input data

        // Example: Convert MultipartFiles to byte arrays
        List<byte[]> imageBytesList = images.stream()
                .map(this::convertMultipartFileToBytes)
                .collect(Collectors.toList());

        return imageBytesList;
    }

    private byte[] convertMultipartFileToBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            // Handle exception (e.g., log, throw custom exception)
            e.printStackTrace();
            return null;
        }
    }



}

