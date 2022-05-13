package com.study.jwttutorial.domain.usr.api;

import com.study.jwttutorial.domain.usr.dto.UsrDto;
import com.study.jwttutorial.domain.usr.entity.Usr;
import com.study.jwttutorial.domain.usr.service.UsrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/usr")
@RestController
public class UsrApi {

    private final UsrService usrService;

    @PostMapping(value = "/signup")
    public ResponseEntity<Usr> signup(@Valid @RequestBody UsrDto.Insert insert) {
        return ResponseEntity.ok(usrService.signup(insert));
    }

    @GetMapping(value = "/search1")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Usr> search1() {
        return ResponseEntity.ok(usrService.getMyUserWithAuthorities().get());
    }

    @GetMapping(value = "/search2/{nm}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Usr> search2(@PathVariable String nm) {
        return ResponseEntity.ok(usrService.getUserWithAuthorities(nm).get());
    }

}
