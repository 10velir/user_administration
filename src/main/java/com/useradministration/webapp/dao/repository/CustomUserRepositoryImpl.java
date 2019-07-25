package com.useradministration.webapp.dao.repository;

import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dao.entity.UserAccount_;
import com.useradministration.webapp.dao.entity.enumeration.Role;
import com.useradministration.webapp.dto.UserSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    @Override
    public List<UserAccount> getWithFilters(UserSearchDto searchDto) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserAccount> criteria = cb.createQuery(UserAccount.class);
        Root<UserAccount> root = criteria.from(UserAccount.class);
        criteria.select(root);

        if (!searchDto.getUserName().equals("")) {
            criteria.where(
                    cb.equal(root.get(UserAccount_.username), searchDto.getUserName())
            );
        }
        if (!searchDto.getRole().equals("")) {
            criteria.where(
                    cb.equal(root.get(UserAccount_.role), Role.valueOf(searchDto.getRole()))
            );
        }

        return entityManager.createQuery(criteria).getResultList();
    }
}
