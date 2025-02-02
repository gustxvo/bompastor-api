package br.com.gustavoalmeidacarvalho.operariosapi.api.controller;

import br.com.gustavoalmeidacarvalho.operariosapi.api.model.sector.LeaderIdInput;
import br.com.gustavoalmeidacarvalho.operariosapi.api.model.user.UserDto;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<UserDto> listUsers() {
        return adminService.getAllUsers().stream()
                .map(UserDto::fromDomain)
                .toList();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") String userId) {
        User user = adminService.findUserById(UUID.fromString(userId));
        return ResponseEntity.ok(UserDto.fromDomain(user));
    }

    @PatchMapping("/sector/{sectorId}/change-leader")
    public ResponseEntity<Void> changeSectorLeader(@PathVariable("sectorId") Integer sectorId, @RequestBody LeaderIdInput leaderId) {
        adminService.changeSectorLeader(sectorId, leaderId.uuid());

        return ResponseEntity.noContent().build();
    }

}
