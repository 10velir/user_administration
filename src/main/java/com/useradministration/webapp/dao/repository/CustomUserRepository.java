package com.useradministration.webapp.dao.repository;

import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dto.UserSearchDto;

import java.util.List;

public interface CustomUserRepository {

    List<UserAccount> getWithFilters(UserSearchDto searchDto);
}
