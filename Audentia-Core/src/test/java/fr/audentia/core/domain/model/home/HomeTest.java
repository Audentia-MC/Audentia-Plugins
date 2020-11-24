package fr.audentia.core.domain.model.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeTest {

    private Home home;

    @BeforeEach
    void setUp() {
        home = new Home("", 0, 0, 0);
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(home.equals(home)).isTrue();
    }

    @Test
    @DisplayName("equals should be true when compare to equals instance")
    void equals_shouldReturnTrue_whenCompareToEqualsInstance() {

        Home otherHome = new Home("", 0, 0, 0);

        assertThat(home.equals(otherHome)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard() {


        Home otherHome = new Home("", 1, 0, 0);

        assertThat(home.equals(otherHome)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard2() {


        Home otherHome = new Home("", 0, 1, 0);

        assertThat(home.equals(otherHome)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard3() {


        Home otherHome = new Home("", 0, 0, 1);

        assertThat(home.equals(otherHome)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

            assertThat(home).isNotNull();
    }


    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(home.hashCode()).isEqualTo(Objects.hash(0, 0, 0));
    }

}

