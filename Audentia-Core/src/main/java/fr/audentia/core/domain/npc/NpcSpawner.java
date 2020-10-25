package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

public interface NpcSpawner {

    void spawnNpc(Npc npc);

    void spawnNpc(Npc npc, String worldName);

    void deleteNpc(Npc npc);

    void deleteNpc(Npc npc, String worldName);

}
