package dev.quang.identity_service.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.quang.identity_service.Dto.Request.InstropectRequest;
import dev.quang.identity_service.Dto.Request.LoginRequest;
import dev.quang.identity_service.Dto.Response.InstropectResponse;
import dev.quang.identity_service.Dto.Response.LoginResponse;
import dev.quang.identity_service.Exception.AppException;
import dev.quang.identity_service.Exception.ErrorCode;
import dev.quang.identity_service.Respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal =  true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey = 
        "Wu43RbJ1wrVutmnWctn4mncX6syRHZL5yXcYbVsxAJMBGpcnXvFGqIswvTfgBJwm";

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    
    public InstropectResponse instropect(InstropectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirityDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        var valid = verified && expirityDate.after(new Date()); 

        if (!valid) throw new AppException(ErrorCode.TOKEN_INVALID);

        return InstropectResponse.builder()
            .valid(valid)
            .build();
    }
    
    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        var authenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticate) {
            throw new AppException(ErrorCode.UNAUTHENTICATE);
        }

        var token = genToken(request.getEmail());

        return LoginResponse.builder()
            .result(authenticate)
            .token(token)
            .build();
    } 

    private String genToken(String email) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
            .subject(email)
            .issuer("dev.quang")
            .issueTime(new Date())
            .expirationTime(new Date(
                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
            ))
            .build();

        Payload payload = new Payload(claimsSet.toJSONObject());    

        JWSObject object = new JWSObject(header, payload);

        try {
            object.sign(new MACSigner(signerKey.getBytes()));
        } catch (JOSEException  e) {
            log.error("Can not create token" , e);
            throw new RuntimeException(e);
        } 
        return object.serialize();   
    }
}
