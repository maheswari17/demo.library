package com.library.authentication.service;
import com.library.authentication.dto.AuthenticationDto;
import com.library.authentication.model.Authentication;
import com.library.authentication.repository.AuthenticationRepository;
import com.library.exceptions.CustomNotFoundException.AuthenticationNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private AuthenticationRepository authenticationRepository;
    private ModelMapper modelMapper;
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository,ModelMapper modelMapper) {
        this.authenticationRepository = authenticationRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public List<AuthenticationDto> getAllAuthentications() {
        return authenticationRepository.findAll().stream().map(this::buildAuthenticationDto).collect(Collectors.toList());
    }

    @Override
    public AuthenticationDto addAuthentication(AuthenticationDto authenticationDto) {
        Authentication authentication= authenticationRepository.save(buildAuthentication(authenticationDto));
        return buildAuthenticationDto(authentication);
    }

    @Override
    public AuthenticationDto getAuthentication(long id) {
        Optional<Authentication> authentication = authenticationRepository.findById(id);
        if(authentication.isPresent()) {
            return buildAuthenticationDto(authentication.get());
        }throw new AuthenticationNotFoundException("authentication details not found");
    }

    @Override
    public void deleteAuthentication(long id) {
        try {
            authenticationRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new AuthenticationNotFoundException("authentication with " + id + " doesn't exist");
        }
    }

    public AuthenticationDto buildAuthenticationDto(Authentication authentication)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AuthenticationDto authenticationDto = modelMapper.map(authentication,AuthenticationDto.class);
        return authenticationDto;
    }

    public Authentication buildAuthentication(AuthenticationDto authenticationDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Authentication authentication = modelMapper.map(authenticationDto,Authentication.class);
        return authentication;
    }
}