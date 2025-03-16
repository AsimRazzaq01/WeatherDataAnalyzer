/**
 * JAVA RECORDS - weather data entry
 * @param Date - entry of weather date
 * @param Temperature - temperature recorded on said date
 * @param Humidity - humidity level recorded on said date
 * @param Precipitation - precipitation level
 */
public record WeatherData(String Date, double Temperature, int Humidity, double Precipitation) {
}

// (Date,Temperature,Humidity,Precipitation) -> data in csv file format