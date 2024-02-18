package com.rentup.RentUp.services;

import com.rentup.RentUp.dto.AreaDTO;
import com.rentup.RentUp.entities.Area;
import com.rentup.RentUp.entities.City;
import com.rentup.RentUp.repository.AreaRepository;
import com.rentup.RentUp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaRepository areaRepo;

    @Autowired
    private CityRepository cityRepo;

    @Override
    public List<String> getAllAreas() {
        List<Area> areaEntities = areaRepo.findAll();
        List<String> areaNames = areaEntities.stream()
                .map(Area::getAreaName).collect(Collectors.toList());
        return areaNames;
    }

    @Override
    public List<String> getAreaByCityName(String cityName) {
        City city = cityRepo.findByCityName(cityName);
        List<Area> areasEntity = areaRepo.findByCity(city);
        List<String> areaNames = areasEntity.stream()
                .map(Area::getAreaName).collect(Collectors.toList());
        return areaNames;
    }

	@Override
	public boolean addArea(AreaDTO area) {
		City city = cityRepo.findByCityName(area.getCity()) ;
		Area newArea = new Area();
		newArea.setCity(city);
		newArea.setAreaName(area.getAreaName());
		Area save = areaRepo.save(newArea) ;
		if(save != null) {
			return true ;
		}
		return false;
	}
}

