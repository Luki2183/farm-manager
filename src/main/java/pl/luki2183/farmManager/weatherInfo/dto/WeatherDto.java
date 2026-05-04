package pl.luki2183.farmManager.weatherInfo.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Exact mapping of the Google Weather API currentConditions response.
 * Jackson ObjectMapper deserializes this automatically via RestTemplate.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("currentTime")
    private String currentTime;

    @JsonProperty("timeZone")
    private TimeZone timeZone;

    @JsonProperty("isDaytime")
    private boolean isDaytime;

    @JsonProperty("weatherCondition")
    private WeatherCondition weatherCondition;

    @JsonProperty("temperature")
    private Temperature temperature;

    @JsonProperty("feelsLikeTemperature")
    private Temperature feelsLikeTemperature;

    @JsonProperty("dewPoint")
    private Temperature dewPoint;

    @JsonProperty("heatIndex")
    private Temperature heatIndex;

    @JsonProperty("windChill")
    private Temperature windChill;

    @JsonProperty("relativeHumidity")
    private int relativeHumidity;

    @JsonProperty("uvIndex")
    private int uvIndex;

    @JsonProperty("precipitation")
    private Precipitation precipitation;

    @JsonProperty("thunderstormProbability")
    private int thunderstormProbability;

    @JsonProperty("airPressure")
    private AirPressure airPressure;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private Visibility visibility;

    @JsonProperty("cloudCover")
    private int cloudCover;

    @JsonProperty("currentConditionsHistory")
    private CurrentConditionsHistory currentConditionsHistory;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TimeZone {
        @JsonProperty("id")
        private String id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherCondition {
        @JsonProperty("iconBaseUri")
        private String iconBaseUri;

        @JsonProperty("description")
        private LocalizedText description;

        @JsonProperty("type")
        private String type;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LocalizedText {
            @JsonProperty("text")
            private String text;

            @JsonProperty("languageCode")
            private String languageCode;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {
        @JsonProperty("degrees")
        private double degrees;

        @JsonProperty("unit")
        private String unit;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Precipitation {
        @JsonProperty("probability")
        private Probability probability;

        @JsonProperty("qpf")
        private Measurement qpf;

        @JsonProperty("snowQpf")
        private Measurement snowQpf;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Probability {
            @JsonProperty("percent")
            private int percent;

            @JsonProperty("type")
            private String type;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Measurement {
            @JsonProperty("quantity")
            private double quantity;

            @JsonProperty("unit")
            private String unit;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AirPressure {
        @JsonProperty("meanSeaLevelMillibars")
        private double meanSeaLevelMillibars;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        @JsonProperty("direction")
        private Direction direction;

        @JsonProperty("speed")
        private WindMeasurement speed;

        @JsonProperty("gust")
        private WindMeasurement gust;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Direction {
            @JsonProperty("degrees")
            private double degrees;

            @JsonProperty("cardinal")
            private String cardinal;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class WindMeasurement {
            @JsonProperty("value")
            private double value;

            @JsonProperty("unit")
            private String unit;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Visibility {
        @JsonProperty("distance")
        private double distance;

        @JsonProperty("unit")
        private String unit;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentConditionsHistory {
        @JsonProperty("temperatureChange")
        private Temperature temperatureChange;

        @JsonProperty("maxTemperature")
        private Temperature maxTemperature;

        @JsonProperty("minTemperature")
        private Temperature minTemperature;

        @JsonProperty("qpf")
        private Precipitation.Measurement qpf;

        @JsonProperty("snowQpf")
        private Precipitation.Measurement snowQpf;
    }
}