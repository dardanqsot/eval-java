package com.dardanqsot.eval.service.Impl;

import com.dardanqsot.eval.dto.UserDto;
import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserSaveResponseDto;
import com.dardanqsot.eval.exception.NotFoundApiException;
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

import java.time.LocalDateTime;
import java.util.Objects;
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
    public UserSaveResponseDto createUser(UserRequestDto userRequestDto) {
        try {
            User user = User.builder()
                    .name(userRequestDto.getName())
                    .email(userRequestDto.getEmail())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .created(LocalDateTime.now())
                    .isActive(Constants.IS_ACTIVE)
                    .modified(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
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
            return modelMapper.map(user, UserSaveResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(Constants.CORREO_EXISTE);
        }
    }

    @Override
    public User findByUuid(UUID uuid) {
        return repo.findByIdUser(uuid);
    }

    @Override
    @Transactional
    public UserSaveResponseDto updateUser(UserDto userDto, UUID uuid) {
        try {
            User user = repo.findByIdUser(uuid);

            if(!Objects.isNull(userDto.getName()))
                user.setName(userDto.getName());
            if(!Objects.isNull(userDto.getEmail()))
                user.setEmail(userDto.getEmail());
            if(!Objects.isNull(userDto.getPassword()))
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(!Objects.isNull(userDto.getIsActive()))
                user.setActive(userDto.getIsActive());

            user.setModified(LocalDateTime.now());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            user.setToken(token);
            repo.save(user);
            return modelMapper.map(user, UserSaveResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(Constants.CORREO_EXISTE);
        }
    }

    @Override
    public void deleteUser(UUID uuid) {
        User user = repo.findByIdUserAndIsActive(uuid, Constants.IS_ACTIVE)
                .orElseThrow(NotFoundApiException.supplier(Constants.NO_FOUND));
        user.setActive(Constants.NO_ACTIVE);
        user.setModified(LocalDateTime.now());
        repo.save(user);
    }
}