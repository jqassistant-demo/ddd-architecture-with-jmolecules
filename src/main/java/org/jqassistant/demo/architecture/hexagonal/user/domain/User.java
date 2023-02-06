package org.jqassistant.demo.architecture.hexagonal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

/**
 * The domain entity defining a user.
 * <p>
 * Note: For convenience reasons this class is already annotated as JPA {@link Entity}.
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

}
