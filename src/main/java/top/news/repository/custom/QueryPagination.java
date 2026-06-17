package top.news.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QueryPagination {

    @Autowired
    private EntityManager entityManager;

    public  <T> Page<T> getPaginationResult(String select, String count,
                                            Map<String, Object> params,
                                            int page, int size,
                                            Class<T> resultClass){

        TypedQuery<T> selectQuery = entityManager.createQuery(select, resultClass);
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);

        Query countQuery = entityManager.createQuery(count);
        params.forEach(countQuery::setParameter);

        List<T> content = selectQuery.getResultList();
        Long totalItem = (Long) countQuery.getSingleResult();

        return new PageImpl<>(content, PageRequest.of(page, size), totalItem);
    }
}
