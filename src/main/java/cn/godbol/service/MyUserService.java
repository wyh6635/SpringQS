package cn.godbol.service;

import cn.godbol.common.service.api.DefaultQueryByEntityService;
import cn.godbol.domain.model.Authority;
import cn.godbol.domain.model.Group;
import cn.godbol.domain.model.User;
import cn.godbol.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Li on 2016/10/15.
 */
@Slf4j
@Service
@Transactional
public class MyUserService implements UserDetailsService, DefaultQueryByEntityService{

    @Inject
    private UserRepository userRepository;

    /**
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user != null) {
            return createSpringUser(user);
        }else {
            throw new UsernameNotFoundException(username + "not found!");
        }
    }

    private org.springframework.security.core.userdetails.User createSpringUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), getAuthorities(user.getGroups()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Group> groups) {
        return getGrantedAuthorities(getPrivileges(groups));
    }

    private List<String> getPrivileges(Collection<Group> groups) {
        List<String> privileges = new ArrayList<>();
        List<Authority> collection = new ArrayList<>();
        for (Group group : groups) {
            collection.addAll(group.getAuthorities());
        }
        for (Authority item : collection) {
            privileges.add(item.getE_name());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public User currentUser(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByUsername(username);
    }

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.userRepository;
    }
}
