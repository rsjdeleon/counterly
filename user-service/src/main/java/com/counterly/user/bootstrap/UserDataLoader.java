package com.counterly.user.bootstrap;

import com.counterly.user.constants.RoleEnum;
import com.counterly.user.model.Authority;
import com.counterly.user.model.Role;
import com.counterly.user.model.User;
import com.counterly.user.repository.AuthorityRepository;
import com.counterly.user.repository.RoleRepository;
import com.counterly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        Authority createCategory = authorityRepository.save(Authority.builder().permission("category_create").build());
        Authority readCategory = authorityRepository.save(Authority.builder().permission("category_read").build());
        Authority updateCategory = authorityRepository.save(Authority.builder().permission("category_update").build());
        Authority deleteCategory = authorityRepository.save(Authority.builder().permission("category_delete").build());

        Authority createProduct = authorityRepository.save(Authority.builder().permission("product_create").build());
        Authority readProduct = authorityRepository.save(Authority.builder().permission("product_read").build());
        Authority updateProduct = authorityRepository.save(Authority.builder().permission("product_update").build());
        Authority deleteProduct = authorityRepository.save(Authority.builder().permission("product_delete").build());

        Authority createProductVariant = authorityRepository.save(Authority.builder().permission("product_variant_create").build());
        Authority readProductVariant = authorityRepository.save(Authority.builder().permission("product_variant_read").build());
        Authority updateProductVariant = authorityRepository.save(Authority.builder().permission("product_variant_update").build());
        Authority deleteProductVariant = authorityRepository.save(Authority.builder().permission("product_variant_delete").build());

        Role adminRole = roleRepository.save(Role.builder().name(RoleEnum.ADMIN.toString()).build());
        Role managerRole = roleRepository.save(Role.builder().name(RoleEnum.MANAGER.toString()).build());
        Role userRole = roleRepository.save(Role.builder().name(RoleEnum.USER.toString()).build());

        adminRole.setAuthorities(new HashSet<>(Set.of(createCategory, updateCategory, readCategory, deleteCategory, createProduct, readProduct,
                updateProduct, deleteProduct, createProductVariant, readProductVariant, updateProductVariant, deleteProductVariant)));

        managerRole.setAuthorities(new HashSet<>(Set.of(readCategory, readProduct, readProductVariant)));

        userRole.setAuthorities(new HashSet<>(Set.of(createCategory, updateCategory, readCategory, createProduct, readProduct,
                updateProduct, createProductVariant, readProductVariant, updateProductVariant)));

        roleRepository.saveAll(Arrays.asList(adminRole, managerRole, userRole));

        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin@counterly"))
                .email("admin@counterly.com")
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("manager")
                .password(passwordEncoder.encode("password@counterly"))
                .email("manager@counterly.com")
                .role(managerRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("user@counterly"))
                .email("user@counterly.com")
                .role(userRole)
                .build());

        log.debug("Users Loaded: {}", userRepository.count());
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
