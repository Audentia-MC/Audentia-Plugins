package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class NpcSpawn {

    private final NpcSpawner npcSpawner;
    private final NpcRepository npcRepository;
    private final WorldNpcFinder worldNpcFinder;
    private final RolesRepository rolesRepository;

    public NpcSpawn(NpcSpawner npcSpawner, NpcRepository npcRepository, WorldNpcFinder worldNpcFinder, RolesRepository rolesRepository) {
        this.npcSpawner = npcSpawner;
        this.npcRepository = npcRepository;
        this.worldNpcFinder = worldNpcFinder;
        this.rolesRepository = rolesRepository;
    }

    public String spawnNpc(String npcName) {

        Optional<Npc> optionalNpc = this.npcRepository.getNpc(npcName);
        String result = buildMessage(npcName, false, " n'a pas été trouvé dans le fichier.");

        if (optionalNpc.isPresent()) {
            this.npcSpawner.spawnNpc(optionalNpc.get());
            result = buildMessage(npcName, true, " a bien spawn.");
        }

        return result;
    }

    public String deleteNpc(String npcName) {

        Optional<Npc> optionalNpc = this.worldNpcFinder.findNpc(npcName);
        String result = buildMessage(npcName, false, " n'a pas été trouvé dans le monde .");

        if (optionalNpc.isPresent()) {
            this.npcSpawner.deleteNpc(optionalNpc.get());
            result = buildMessage(npcName, true, " a bien été supprimé.");
        }

        return result;
    }

    public String spawnAllNpcs() {

        return this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::spawnNpc)
                .collect(Collectors.joining("\n"));
    }

    public String deleteAllNpcs() {

        return this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .map(this::deleteNpc)
                .collect(Collectors.joining("\n"));
    }

    public String reloadNpc(UUID playerUUID, String npcName) {

        Role role = rolesRepository.getRole(playerUUID);

        if (!role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas effectuer cette action.";
        }

        return forceReloadNpc(npcName);
    }

    public String reloadAllNpcs() {

        this.worldNpcFinder.findAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::deleteNpc);

        this.npcRepository.getAllNpc().stream()
                .map(npc -> npc.name)
                .forEach(this::spawnNpc);

        return "<success>Les PNJ ont bien été rechargés.";
    }

    private String buildMessage(String npcName, boolean success, String suffix) {
        return (success ? "<success>" : "<error>") + "Le PNJ " + npcName + suffix;
    }

    public String forceReloadNpc(String npcName) {
        String result = buildMessage(npcName, true, " a bien été rechargé.");

        String error = "<error>";
        String deleteResult = deleteNpc(npcName);
        String spawnResult = spawnNpc(npcName);

        if (deleteResult.contains("<error>")) {
            error += deleteResult;
        }

        if (spawnResult.contains("<error>")) {
            error += "\n" + spawnResult;
        }

        if (error.length() != 7) {
            result = error;
        }

        return result;
    }

}
