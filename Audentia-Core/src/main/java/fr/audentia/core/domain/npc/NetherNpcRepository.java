package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;

import java.util.Optional;

public interface NetherNpcRepository {

    Optional<Npc> getRandomNetherNpcLocation();

    String getNetherNpcName();

}
