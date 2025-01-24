package br.com.gustavoalmeidacarvalho.operariosapi.infra.user.service;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.Sector;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.sector.SectorService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.AdminService;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final SectorService sectorService;

    @Override
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Override
    public User findUserById(UUID userId) {
        return userService.findById(userId);
    }

    @Override
    public void changeSectorLeader(Integer sectorId, UUID leaderId) {
        Sector sector = sectorService.findById(sectorId);
        User leader = userService.findById(leaderId);

        sectorService.changeLeader(sector, leader);
    }
}
