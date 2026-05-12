package top.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.news.dto.region.RegionByLangDTO;
import top.news.dto.region.RegionDTO;
import top.news.entity.Region;
import top.news.exception.ItemNotFoundException;
import top.news.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public String saveRegion(RegionDTO dto) {
        Region region = save(dto);
        region.setVisible(true);
        region.setCreatedDate(LocalDateTime.now());

        regionRepository.save(region);

        return "Successfully created";
    }

    public String updateById(Integer regionId, RegionDTO dto) {
        Optional<Region> optional = regionRepository.findByIdAndVisibleTrue(regionId);

        if(optional.isEmpty()){
            throw new ItemNotFoundException("Region is not found");
        }
        Region region = save(dto);

        regionRepository.save(region);

        return "Successfully updated";

    }

    private Region save(RegionDTO dto){
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

        int result = regionRepository.delete(regionId);

        return (result > 0 ? "Successfully deleted" : "Hmm something went wrong");
    }

    public List<Region> getList() {
        Iterable<Region> regions = regionRepository.findAll();

        List<Region> response = new LinkedList<>();
        for (Region region : regions) {
            response.add(region);
        }
        return response;
    }

    public List<RegionByLangDTO> getRegionByLang(String lang) {
        Iterable<Region> regions = regionRepository.findAllByVisibleTrue();
        List<RegionByLangDTO> response = new LinkedList<>();

        for (Region region : regions) {
            RegionByLangDTO cur = new RegionByLangDTO();
            cur.setId(region.getId());
            cur.setKey(region.getKey());

            switch(lang){
                case "uz" -> cur.setName(region.getNameUz());
                case "ru" -> cur.setName(region.getNameRu());
                case "en" -> cur.setName(region.getNameEn());
            }

            response.add(cur);
        }

        return response;
    }
}
