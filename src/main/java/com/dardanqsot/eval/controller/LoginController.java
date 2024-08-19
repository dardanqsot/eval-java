package com.dardanqsot.eval.controller;

import com.dardanqsot.eval.dto.ResponseDto;
import com.dardanqsot.eval.security.JwtRequest;
import com.dardanqsot.eval.security.JwtResponse;
import com.dardanqsot.eval.security.JwtTokenUtil;
import com.dardanqsot.eval.security.JwtUserDetailsService;
import com.dardanqsot.eval.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtResponse>> login(@RequestBody JwtRequest req) throws Exception{
        authenticate(req.getEmail(), req.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(ResponseDto.successResponse(new JwtResponse(token)), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new UsernameNotFoundException(Constants.CREDENCIALES_INCORRECTAS, e);
        }
    }
}

