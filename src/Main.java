import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        // METHOD TO READ & PARSE WEATHER DATA FROM .csv File
        List<WeatherData> WeatherDataList = Files.lines(Path.of("src/randomized_weather_data_50.csv"))
                        .map(line -> line.split(","))
                                .map(parts ->
                                        new WeatherData(
                                                parts[0],
                                                Double.parseDouble(parts[1]),
                                                Integer.parseInt(parts[2]),
                                                Double.parseDouble(parts[3]))
                                ).toList();


        // Define the processed interface data using the lambda expressions
        WeatherProcessor AvgMonthTempProcessed =data -> "Average temp in September: " +
                AverageTemp(data);

        WeatherProcessor CurrentTempProcessed =data -> "Days w/ temp above 32 degrees Celsius: " +
                checkTempOfDays(data, WeatherData -> WeatherData.Temperature() > 32);

        WeatherProcessor RainyDaysProcessed = data -> "Count rainy days: " +
                RainyDays(data);


        // Text Block - as a string WeatherReport (Formatted)
        String WeatherReport = """
                Weather Report:
                ---------------
                %s
                ---------------
                %s
                ---------------
                %s
                ---------------
                """.formatted(
                        AvgMonthTempProcessed.process(WeatherDataList),
                CurrentTempProcessed.process(WeatherDataList),
                RainyDaysProcessed.process(WeatherDataList)
        );

//                categorized temp for each day
//                /%s
//                categorizeTemperatures(WeatherDataList)

        // Print of text block - weather report
        System.out.println(WeatherReport);

    }   // End of  main method

    /**
     * calculates avg Weather temp from the month of Sept
     * @param dataList - list of WeatherData records
     * @return  avg temp in sept
     */
    private static double AverageTemp(List<WeatherData> dataList) {

        // Choose sept because it's when my birthday is :)
        List<Double> septList = dataList.stream()
                .filter(WeatherData ->WeatherData.Date().contains("2023-09"))
                .map(WeatherData::Temperature)
                .toList();

        return septList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    /**
     * category - Hot, warm, cool, cold
     * @param temp  - temp values
     * @return  - string of temp category
     */
    private static String tempCatagoryChart(double temp) {
        return switch ((int) temp/10){
            case 3,4,5,6,7,8,9 -> "Hot weather";
            case 2 -> "Warm weather";
            case 1 -> "Cool weather";
            default -> "Cold weather";
        };
    }

    /**
     * Filter weather data based on weatherCondition
     * @param dataList  - list of WeatherData records
     * @param weatherCondition  - Predicate filter weathering condition
     * @return  List of dates that meet the weather conditions to be specified
     */
    private static List<String> checkTempOfDays(List<WeatherData> dataList, Predicate<WeatherData> weatherCondition){
        return dataList.stream()
                .filter(weatherCondition)
                .map(WeatherData::Date)
                .toList();
    }

    /**
     * Counts the number of rainy days based on precipitation value
     * @param dataList  - list of WeatherData records
     * @return      - Count of days with precipitation
     */
    private static double RainyDays(List<WeatherData> dataList){
        return dataList.stream()
                .filter(WeatherData ->WeatherData.Precipitation() > 1)
                .count();
    }

    /**
     * categorize temp for every recorded day
     * @param dataList - list of WeatherData records
     * @return  - string of temp categories per date
     */
    private static String categorizeTemperatures(List<WeatherData> dataList){
        return dataList.stream()
                .map(WeatherData ->"%s: %s".formatted(
                        WeatherData.Date(), tempCatagoryChart(WeatherData.Temperature())
                )).collect(Collectors.joining("\n"));
    }




}   // End of Main Class



