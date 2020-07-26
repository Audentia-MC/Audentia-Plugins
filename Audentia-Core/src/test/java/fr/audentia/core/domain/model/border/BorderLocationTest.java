package fr.audentia.core.domain.model.border;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BorderLocationTest {

    private BorderLocation borderLocation;

    @BeforeEach
    void setUp() {
        borderLocation = new BorderLocation(0, 0);
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(borderLocation.equals(borderLocation)).isTrue();
    }

    @Test
    @DisplayName("equals should be true when compare to equals instance")
    void equals_shouldReturnTrue_whenCompareToEqualsInstance() {

        BorderLocation otherBorderLocation = new BorderLocation(0, 0);

        assertThat(borderLocation.equals(otherBorderLocation)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to an other border location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard() {

        BorderLocation otherBorderLocation = new BorderLocation(1, 0);

        assertThat(borderLocation.equals(otherBorderLocation)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to an other border location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard2() {

        BorderLocation otherBorderLocation = new BorderLocation(0, 1);

        assertThat(borderLocation.equals(otherBorderLocation)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

        assertThat(borderLocation.equals(null)).isFalse();
    }


    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(borderLocation.hashCode()).isEqualTo(Objects.hash(0, 0));
    }

}
