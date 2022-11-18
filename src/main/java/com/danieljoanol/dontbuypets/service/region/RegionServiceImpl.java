package com.danieljoanol.dontbuypets.service.region;

import org.springframework.stereotype.Service;

import com.danieljoanol.dontbuypets.entity.Region;
import com.danieljoanol.dontbuypets.repository.RegionRepository;
import com.danieljoanol.dontbuypets.service.generic.GenericServiceImpl;

@Service
public class RegionServiceImpl extends GenericServiceImpl<Region> implements RegionService {
    
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        super(regionRepository);
        this.regionRepository = regionRepository;
    }

    @Override
    public Region create(Region region) {
        region.setId(null);
        return regionRepository.save(region);
    }

}
