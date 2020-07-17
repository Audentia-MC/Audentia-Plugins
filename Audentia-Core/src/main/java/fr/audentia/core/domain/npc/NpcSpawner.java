package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

public interface NpcSpawner {

    void spawnNpc(Npc npc);

    void deleteNpcNpc(Npc npc);

}
