package org.launchcode.library.security;

import org.launchcode.library.models.data.UserRepository;
import org.launchcode.library.models.Role;
import org.launchcode.library.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

//verifying user by username while login

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //User user = userRepository.findByEmail(email);
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            Collection<? extends GrantedAuthority> grantedRoles = mapRolesToAuthorities(user.getRoles());
            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    //return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),
                    grantedRoles);
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    //Role is application specific object but spring security understands only GrantedAuthority, so this method
    // is converting the collection of Role object to collection of GrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
