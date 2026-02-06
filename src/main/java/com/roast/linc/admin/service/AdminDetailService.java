package com.roast.linc.admin.service;

import com.roast.linc.admin.entity.AdminUser;
import com.roast.linc.admin.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminDetailService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı: " + username));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // DB'deki hashli şifre
                .roles("ADMIN")
                .build();
    }
}
