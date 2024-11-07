package com.croco.dispatcherdbcontroller.mapper;

import com.croco.dispatcherdbcontroller.dto.UserDto;
import com.croco.dispatcherdbcontroller.entity.User;
import com.croco.dispatcherdbcontroller.entity.UserStatus;
import com.croco.dispatcherdbcontroller.entity.UserType;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "userStatus", source = "userDto.userStatus", qualifiedByName = "mapStringToUserStatus")
    @Mapping(target = "userType", source = "userDto.userType", qualifiedByName = "mapStringToUserType")
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "userStatus", source = "user.userStatus", qualifiedByName = "mapUserStatusToString")
    @Mapping(target = "userType", source = "user.userType", qualifiedByName = "mapUserTypeToString")
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userStatus", source = "userDto.userStatus", qualifiedByName = "mapStringToUserStatus")
    @Mapping(target = "userType", source = "userDto.userType", qualifiedByName = "mapStringToUserType")
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    @Mapping(target = "userStatus", source = "user.userStatus", qualifiedByName = "mapUserStatusToString")
    @Mapping(target = "userType", source = "user.userType", qualifiedByName = "mapUserTypeToString")
    List<UserDto> toDto(List<User> user);

    // Методы для маппинга UserStatus в строку
    @Named("mapUserStatusToString")
    default String mapUserStatusToString(UserStatus userStatus) {
        return userStatus != null ? userStatus.name() : null;
    }

    // Метод для маппинга UserType в строку
    @Named("mapUserTypeToString")
    default String mapUserTypeToString(UserType userType) {
        return userType != null ? userType.name() : null;
    }

    // Метод для маппинга UserStatus из строки в перечисление
    @Named("mapStringToUserStatus")
    default UserStatus mapStringToUserStatus(String userStatus) {
        return userStatus != null ? UserStatus.valueOf(userStatus) : null;
    }

    // Метод для маппинга UserType из строки в перечисление
    @Named("mapStringToUserType")
    default UserType mapStringToUserType(String userType) {
        return userType != null ? UserType.valueOf(userType) : null;
    }
}