package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.List;
import java.util.Optional;

public interface WorldNpcFinder {

    Optional<Npc> findNpc(String npcName);

    Optional<Npc> findNpc(String npcName, String worldName);

    List<Npc> findAllNpc();

}
