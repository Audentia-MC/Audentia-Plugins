package fr.audentia.core.domain.model.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = aScoreboard()
                .withHeader("header")
                .withContent(Arrays.asList("1", "2"))
                .withFooter("footer")
                .build();
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(scoreboard.equals(scoreboard)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to an other scoreboard")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard() {


        Scoreboard otherScoreboard = aScoreboard()
                .withHeader("")
                .withContent(Collections.emptyList())
                .withFooter("footer")
                .build();

        assertThat(scoreboard.equals(otherScoreboard)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

        assertThat(scoreboard.equals(null)).isFalse();
    }


    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(scoreboard.hashCode()).isEqualTo(Objects.hash("header", Arrays.asList("1", "2"), "footer"));
    }

}
