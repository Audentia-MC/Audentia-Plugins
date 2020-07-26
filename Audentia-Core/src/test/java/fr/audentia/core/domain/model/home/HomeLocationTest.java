package fr.audentia.core.domain.model.home;

import fr.audentia.core.domain.model.border.BorderSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeLocationTest {

    private HomeLocation homeLocation;

    @BeforeEach
    void setUp() {
        homeLocation = new HomeLocation(0, 0, 0);
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(homeLocation.equals(homeLocation)).isTrue();
    }

    @Test
    @DisplayName("equals should be true when compare to equals instance")
    void equals_shouldReturnTrue_whenCompareToEqualsInstance() {

        HomeLocation otherHomeLocation = new HomeLocation(0, 0, 0);

        assertThat(homeLocation.equals(otherHomeLocation)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard() {


        HomeLocation otherHomeLocation = new HomeLocation(1, 0, 0);

        assertThat(homeLocation.equals(otherHomeLocation)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard2() {


        HomeLocation otherHomeLocation = new HomeLocation(0, 1, 0);

        assertThat(homeLocation.equals(otherHomeLocation)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to an other home location")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard3() {


        HomeLocation otherHomeLocation = new HomeLocation(0, 0, 1);

        assertThat(homeLocation.equals(otherHomeLocation)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

        assertThat(homeLocation.equals(null)).isFalse();
    }


    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(homeLocation.hashCode()).isEqualTo(Objects.hash(0, 0, 0));
    }

}

