package com.useradministration.webapp.dao.repository;

import com.useradministration.webapp.dao.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long>, CustomUserRepository{

    UserAccount findByUsername(String s);

    @Query("select u from UserAccount u where u.username =:userName")
    Optional<UserAccount> getUserByLogin(@Param("userName") String login);
}
