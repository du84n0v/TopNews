package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.region.RegionRequestDTO;
import top.news.dto.region.RegionResponseDTO;
import top.news.entity.Region;
import top.news.enums.AppLanguage;
import top.news.exception.AppBadRequestException;
import top.news.exception.ItemNotFoundException;
import top.news.mapper.RegionMapper;
import top.news.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public String saveRegion(RegionRequestDTO dto) {
        Optional<Region> optional = regionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(optional.isPresent()){
            throw new AppBadRequestException("Key already exists");
        }
        Region region = save(dto);
        region.setVisible(Boolean.TRUE);
        region.setCreatedDate(LocalDateTime.now());

        regionRepository.save(region);

        return "Successfully created";
    }

    public String updateById(Integer regionId, RegionRequestDTO dto) {
        Optional<Region> optional = regionRepository.findByIdAndVisibleTrue(regionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Region not found");
        }
        Optional<Region> keyOptional = regionRepository.findByKeyAndVisibleTrue(dto.getKey());
        if(keyOptional.isPresent() && !regionId.equals(keyOptional.get().getId())){
            throw new AppBadRequestException("Key already exists");
        }
        Region region = save(dto);
        region.setId(regionId);
        region.setVisible(Boolean.TRUE);
        region.setCreatedDate(optional.get().getCreatedDate());
        regionRepository.save(region);

        return "Successfully updated";
    }

    private Region save(RegionRequestDTO dto){
        Region region = new Region();
        region.setOrderNumber(dto.getOrderNumber());
        region.setNameUz(dto.getNameUz());
        region.setNameRu(dto.getNameRu());
        region.setNameEn(dto.getNameEn());
        region.setKey(dto.getKey());
        return region;
    }

    public String deleteRegion(Integer regionId) {
        Optional<Region> optional = regionRepository.findByIdAndVisibleTrue(regionId);
        if(optional.isEmpty()){
            throw new ItemNotFoundException("Region is not found");
        }
        return (regionRepository.delete(regionId) > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Region> getList() {
        Iterable<Region> regions = regionRepository.findAllByVisibleTrue();

        List<Region> response = new LinkedList<>();
        for (Region region : regions) {
            response.add(region);
        }
        return response;
    }

    public List<RegionResponseDTO> getRegionByLang(AppLanguage lang) {
        List<RegionMapper> mappers = regionRepository.findAllByLang(lang.name());
        List<RegionResponseDTO> response = new LinkedList<>();
        for (RegionMapper mapper : mappers) {
            RegionResponseDTO dto = new RegionResponseDTO();
            dto.setId(mapper.getId());
            dto.setKey(mapper.getKey());
            dto.setName(mapper.getName());
            response.add(dto);
        }
        return response;
    }
}
