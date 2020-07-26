package fr.audentia.core.domain.model.border;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BorderSizeTest {

    private BorderSize borderSize;

    @BeforeEach
    void setUp() {
        borderSize = new BorderSize(0);
    }

    @Test
    @DisplayName("equals should be true when compare same instance")
    void equals_shouldReturnTrue_whenCompareSameInstance() {

        assertThat(borderSize.equals(borderSize)).isTrue();
    }

    @Test
    @DisplayName("equals should be true when compare to equals instance")
    void equals_shouldReturnTrue_whenCompareToEqualsInstance() {

        BorderSize otherBorderSize = new BorderSize(0);

        assertThat(borderSize.equals(otherBorderSize)).isTrue();
    }

    @Test
    @DisplayName("equals should be false when compare to an other border size")
    void equals_shouldReturnFalse_whenCompareToAnOtherScoreboard() {


        BorderSize otherBorderSize = new BorderSize(1);

        assertThat(borderSize.equals(otherBorderSize)).isFalse();
    }

    @Test
    @DisplayName("equals should be false when compare to null")
    void equals_shouldReturnFalse_whenCompareToNull() {

        assertThat(borderSize.equals(null)).isFalse();
    }


    @Test
    @DisplayName("hashCode should return hash")
    void hash_shouldReturnHash() {

        assertThat(borderSize.hashCode()).isEqualTo(Objects.hash(0));
    }

}
