package fr.audentia.core.domain.npc.spawn;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.List;
import java.util.Optional;

public interface WorldNpcFinder {

    Optional<Npc> findNpc(String npcName);

    List<Npc> findAllNpc();

}
