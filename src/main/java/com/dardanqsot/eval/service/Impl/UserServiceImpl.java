package com.dardanqsot.eval.service.Impl;

import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserResponseDto;
import com.dardanqsot.eval.model.Phone;
import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.repository.GenericRepo;
import com.dardanqsot.eval.repository.PhoneRepository;
import com.dardanqsot.eval.repository.UserRepository;
import com.dardanqsot.eval.security.JwtTokenUtil;
import com.dardanqsot.eval.security.JwtUserDetailsService;
import com.dardanqsot.eval.service.UserService;
import com.dardanqsot.eval.util.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository repo;
    private final PhoneRepository phoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    @Override
    protected GenericRepo<User, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try {
            User user = User.builder()
                    .name(userRequestDto.getName())
                    .email(userRequestDto.getEmail())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .created(LocalDate.now())
                    .isActive(Constants.IS_ACTIVE)
                    .modified(LocalDate.now())
                    .lastLogin(LocalDate.now())
                    .build();
            repo.save(user);
            userRequestDto.getPhones().forEach(p -> {
                Phone phone = Phone.builder()
                        .user(user)
                        .number(p.getNumber())
                        .cityCode(p.getCityCode())
                        .countryCode(p.getCountryCode())
                        .build();
                phoneRepository.save(phone);
            });
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            user.setToken(token);
            repo.save(user);
            return modelMapper.map(user, UserResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(Constants.CORREO_EXISTE);
        }
    }

    @Override
    public User findByUuid(UUID uuid) {
        return repo.findByIdUser(uuid);
    }

    @Override
    public User updateUser(UserRequestDto userRequestDto, UUID uuid) {
        try {
            User user = repo.findByIdUser(uuid);
            user.setName(userRequestDto.getName());
            user.setEmail(userRequestDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            user.setModified(LocalDate.now());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            user.setToken(token);
            repo.save(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(Constants.CORREO_EXISTE);
        }
    }

    @Override
    public void deleteUser(UUID uuid) {
        User user = repo.findByIdUser(uuid);
        repo.delete(user);
    }
}