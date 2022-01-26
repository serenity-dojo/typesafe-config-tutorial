package hocon;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WhenReadingTypesafeConfigProperties {
    @Test
    void readingPropertiesFromAString() {
        Config config = ConfigFactory.parseString("color: red");

        String value = config.getString("color");

        assertThat(value).isEqualTo("red");
    }

    @Test
    void readingIntegerProperties() {
        Config config = ConfigFactory.parseString("count: 100");

        int count = config.getInt("count");

        assertThat(count).isEqualTo(100);
    }

    @Nested
    class WhenReadingFromAConfigFile {

        Config config = ConfigFactory.parseURL(getClass().getClassLoader().getResource("my-config.conf"));

        @Test
        void readingConfigFromAFile() {
            assertThat(config.getString("favoriteColor")).isEqualTo("RED");
        }

        @Test
        void readingEnums() {
            assertThat(config.getEnum(Color.class, "favoriteColor")).isEqualTo(Color.RED);
        }

        @Test
        void readingAListOfInts() {
            assertThat(config.getIntList("favoriteNumbers")).hasSize(3).contains(1,2,3);
        }

        @Test
        void readNestedProperties() {
            assertThat(config.resolve().getString("users.joe.fullName")).isEqualTo("Joe Bloggs");
        }

        @Test
        void readTimeUnit() {
            assertThat(config.resolve().getDuration("uses.joe.timeout")).isEqualTo("Joe Bloggs");
        }

        @Test
        void readTimeUnits() {
            assertThat(config.resolve().getDurationList("uses.joe.timeouts")).hasSize(3);
        }

    }


}
