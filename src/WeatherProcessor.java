import java.util.List;
/**
 *  Functional Interface for processing the Weather Data
 */
public interface WeatherProcessor {
    /**
     * process weather data and return string
     * @param data  - List of WeatherData records
     * @return  processed weather data result as a string
     */
    String process(List<WeatherData> data);
}
