package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.List;
import java.util.Optional;

public interface NpcRepository {

    Optional<Npc> getNpc(String pnjName);

    List<Npc> getAllNpc();

}
