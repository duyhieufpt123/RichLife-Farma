package richlife.user.RichLife_User.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import richlife.user.RichLife_User.entities.User;
import richlife.user.RichLife_User.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email.")));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with Username -" +
                    username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), authorities);
    }
}
