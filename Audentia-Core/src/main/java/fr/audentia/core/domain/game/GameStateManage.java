package fr.audentia.core.domain.game;

public class GameStateManage {

    private final GamesInfosRepository gamesInfosRepository;

    public GameStateManage(GamesInfosRepository gamesInfosRepository) {
        this.gamesInfosRepository = gamesInfosRepository;
    }

    public boolean isPlaying() {

        GameState currentState = this.gamesInfosRepository.getGameState();

        return currentState == GameState.PLAYING;
    }

}
