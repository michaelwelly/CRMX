package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.entity.UserStatus;
import com.croco.dispatcherdbcontroller.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Получает список пользователей по указанному типу.
     *
     * @param userType тип пользователя для фильтрации
     * @return Optional, содержащий список пользователей, соответствующих указанному типу.
     */
    Optional<List<User>> findByUserType(UserType userType);

    /**
     * Получает список пользователей по указанному типу и статусу.
     *
     * @param userType тип пользователя для фильтрации
     * @param userStatus статус пользователя для фильтрации (опционально)
     * @return Optional, содержащий список пользователей, соответствующих указанному типу и статусу.
     */
    @Query("SELECT u FROM User u WHERE u.userType = :userType AND (:userStatus IS NULL OR u.userStatus = :userStatus)")
    Optional<List<User>> findByUserTypeAndOptionalStatus(@Param("userType") UserType userType, @Param("userStatus") UserStatus userStatus);

    Optional<User> findByNameStrAndUserNameStrAndUserType(String nameStr, String userNameStr, UserType userType);

    Optional<List<User>> findByUserNameStrEquals(String nameStr);

    @Query("SELECT COALESCE(MAX(u.id), 0) FROM User u")
    Long findMaxId();
}
