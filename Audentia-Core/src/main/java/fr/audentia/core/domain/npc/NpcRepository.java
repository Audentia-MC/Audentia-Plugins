package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.NpcLocation;

import java.util.Optional;

public interface NpcRepository {

    Optional<NpcLocation> getPnjLocation(String pnjName);

}
