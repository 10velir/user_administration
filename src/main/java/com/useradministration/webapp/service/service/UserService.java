package com.useradministration.webapp.service.service;

import com.useradministration.webapp.dao.entity.UserAccount;
import com.useradministration.webapp.dao.entity.enumeration.Role;
import com.useradministration.webapp.dao.entity.enumeration.UserStatus;
import com.useradministration.webapp.dao.repository.UserRepository;
import com.useradministration.webapp.dto.UserDto;
import com.useradministration.webapp.dto.UserSearchDto;
import com.useradministration.webapp.exception.NotValidPasswordException;
import com.useradministration.webapp.exception.NotValidUserDetailsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static com.useradministration.webapp.mapper.Mapper.MapDtoToEntity;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    public Optional<UserAccount> getById(Long id) {
        return userRepository.findById(id);
    }

    public Page<UserAccount> getAll(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 9));
    }

    public List<UserAccount> getAll() {
        return userRepository.findAll();
    }

    public Page<UserAccount> getWithFilters(UserSearchDto searchDto) {

        return new PageImpl<>(userRepository.getWithFilters(searchDto));
    }

    public Optional<UserAccount> getByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Transactional
    public UserAccount saveOrUpdate(UserAccount account) {
        return userRepository.save(account);
    }

    @Transactional
    public UserAccount saveOrUpdate(UserDto dto, UserAccount account) throws NotValidPasswordException, ConstraintViolationException {
        if (!dto.getPasswordQ().equals("")) {
            if (dto.getPasswordQ().matches("^(?=.*?[a-zA-Z])(?=.*?[0-9]).{3,16}$")) {
                dto.setPasswordQ(encoder.encode(dto.getPasswordQ()));
            } else throw new NotValidPasswordException("password must contain Latin characters and numbers" +
                    "and must be between 3 and 16 characters");
        }
        if (dto.getStatusQ().equals("ACTIVE")) {
            account.setStatus(UserStatus.ACTIVE);
        } else if (dto.getStatusQ().equals("INACTIVE")) {
            account.setStatus(UserStatus.INACTIVE);
        }
        if (!dto.getUsernameQ().equals("")) {
            account.setUsername(dto.getUsernameQ());
        }
        if (!dto.getFirstNameQ().equals("")) {
            account.setFirstName(dto.getFirstNameQ());
        }
        if (!dto.getLastNameQ().equals("")) {
            account.setLastName(dto.getLastNameQ());
        }
        //Mapper.MapDtoToEntity(dto, account, null);
        return userRepository.save(account);
    }

    @Transactional
    public void delete(Long accountId) {
        userRepository.deleteById(accountId);
    }

    @Transactional
    public UserAccount register(UserDto dto, UserAccount account) throws NotValidPasswordException {
        if (!dto.getPasswordQ().equals("") && dto.getPasswordQ().matches("^(?=.*?[a-zA-Z])(?=.*?[0-9]).{3,16}$")) {
            dto.setPasswordQ(encoder.encode(dto.getPasswordQ()));
        } else throw new NotValidPasswordException("password must contain Latin characters and numbers" +
                "and must be between 3 and 16 characters");
        MapDtoToEntity(dto, account, List.of("role"), "usernameQ->username",
                "passwordQ->password", "firstNameQ->firstName", "lastNameQ->lastName");

        if (dto.getRoleQ().equals("ADMIN")) {
            account.setRole(Role.ADMIN);
        } else if (dto.getRoleQ().equals("USER")) {
            account.setRole(Role.USER);
        }
        account.setStatus(UserStatus.ACTIVE);
        UserAccount userAccount;
        try {
            userAccount = userRepository.save(account);
        } catch (ConstraintViolationException ex) {
            throw new NotValidUserDetailsException("INVALID USER DETAILS: " + ex.getMessage());
        }
        return userAccount;
    }

}
