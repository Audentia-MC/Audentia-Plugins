package fr.audentia.core.infrastructure.npc.repositories;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.spawn.NpcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MariaDbNpcRepository implements NpcRepository {

    @Override
    public Optional<Npc> getNpc(String pnjName) {
        return Optional.empty();
    }

    @Override
    public List<Npc> getAllNpc() {
        return new ArrayList<>();
    }

}
