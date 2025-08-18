package com.counterly.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority extends BaseEntity {

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

}
