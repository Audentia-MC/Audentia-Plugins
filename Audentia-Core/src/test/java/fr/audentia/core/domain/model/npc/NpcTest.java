package fr.audentia.core.domain.model.npc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;
import static org.assertj.core.api.Assertions.assertThat;

class NpcTest {

    private Npc npc;

    @BeforeEach
    void setUp() {
        npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(npc.equals(npc)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

        assertThat(npc.equals(null)).isFalse();
    }



    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(npc.hashCode()).isEqualTo(Objects.hash("Tony", 0, 0, 0, 0, 0));
    }

}
