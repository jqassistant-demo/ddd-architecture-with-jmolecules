package org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1;

import org.jqassistant.demo.architecture.hexagonal.user.domain.User;
import org.mapstruct.Mapper;

/**
 * Defines of the mapper between the domain model and the DTOs of the users REST API.
 * <p>
 * The actual implementation is generated using MapStruct.
 */
@Mapper
public interface UsersApiMapper {

    User toDomain(org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.User user);

    org.jqassistant.demo.architecture.hexagonal.user.adapters.primary.rest.v1.users.model.User fromDomain(User user);

}
