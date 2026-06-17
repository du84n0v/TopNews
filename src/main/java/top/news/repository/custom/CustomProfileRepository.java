package top.news.repository.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import top.news.dto.profile.ProfileFilterDTO;
import top.news.entity.Profile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomProfileRepository {

    @Autowired
    private QueryPagination queryPagination;

    public Page<Profile> filter(ProfileFilterDTO dto, Integer page, Integer size) {
        StringBuilder select = new StringBuilder("SELECT DISTINCT p FROM Profile p ");
        StringBuilder count = new StringBuilder("SELECT COUNT(DISTINCT p) FROM Profile p ");
        Map<String, Object> params = new HashMap<>();
        StringBuilder filter = new StringBuilder(" WHERE p.visible=true ");

        if (dto.getRoleList() != null && !dto.getRoleList().isEmpty()) {
            select.append(" JOIN p.roleList pr ");
            count.append(" JOIN p.roleList pr ");
            filter.append(" AND pr.role IN (:roles) ");
            params.put("roles", dto.getRoleList());
        }
        if(dto.getName() != null){
            filter.append(" AND LOWER(p.name) LIKE :name ");
            params.put("name", '%' + dto.getName().toLowerCase() + "%");
        }
        if(dto.getSurname() != null){
            filter.append(" AND LOWER(p.surname) LIKE :surname ");
            params.put("surname", '%' + dto.getSurname().toLowerCase() + "%");
        }
        if(dto.getUsername() != null){
            filter.append(" AND LOWER(p.username) LIKE :username ");
            params.put("username", '%' + dto.getUsername().toLowerCase() + "%");
        }
        if(dto.getDateFrom() != null){
            LocalDateTime from = dto.getDateFrom().atStartOfDay();

            filter.append(" AND p.createdDate >= :from ");
            params.put("from", from);
        }
        if(dto.getDateTo() != null){
            LocalDateTime to = dto.getDateTo().atTime(LocalTime.MAX);

            filter.append(" AND p.createdDate <= :to ");
            params.put("to", to);
        }

        select.append(filter);
        count.append(filter);

        return queryPagination.getPaginationResult(
                select.toString(),
                count.toString(),
                params,
                page,
                size,
                Profile.class);

    }
}
