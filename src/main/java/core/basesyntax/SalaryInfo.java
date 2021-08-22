package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final int COUNT_DATA_VARIABLES = 4;
    private static final int INDEX_OUTDATA_NAME = 0;
    private static final int INDEX_OUTDATA_MONEY = 1;
    private static final int INDEX_OUTDATA_FIELDS_ALL = 2;
    private static final int INDEX_DATA_DATE = 0;
    private static final int INDEX_DATA_NAME = 1;
    private static final int INDEX_DATA_TIME = 2;
    private static final int INDEX_DATA_MONEY = 3;
    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        String[][] table = new String[data.length][COUNT_DATA_VARIABLES];
        String[][] outData = new String[INDEX_OUTDATA_FIELDS_ALL][names.length];
        for (int i = 0; i < outData[0].length; i++) {
            outData[INDEX_OUTDATA_NAME][i] = names[i];
            outData[INDEX_OUTDATA_MONEY][i] = "0";
        }
        for (int i = 0; i < data.length; i++) {
            table[i] = data[i].split(" ");
            if (isDataOk(table[i][INDEX_DATA_DATE], dateFrom, dateTo)) {
                for (int j = 0; j < outData[0].length; j++) {
                    if (table[i][INDEX_DATA_NAME].equals(names[j])) {
                        outData[INDEX_OUTDATA_MONEY][j] = String.valueOf(Integer
                               .parseInt(outData[INDEX_OUTDATA_MONEY][j])
                               + Integer.parseInt(table[i][INDEX_DATA_TIME])
                               * Integer.parseInt(table[i][INDEX_DATA_MONEY]));
                    }
                }
            }
        }
        return result(outData, names, dateFrom, dateTo);
    }

    public String result(String[][] result, String[] names, String dateFrom, String dateTo) {
        StringBuilder outString = new StringBuilder("Report for period ")
                .append(dateFrom).append(" - ").append(dateTo)
                .append(System.lineSeparator());
        for (int i = 0; i < names.length; i++) {
            if (i != names.length - 1) {
                outString.append(names[i]).append(" - ")
                        .append(result[INDEX_OUTDATA_MONEY][i])
                        .append(System.lineSeparator());
            } else {
                outString.append(names[i]).append(" - ")
                        .append(result[INDEX_OUTDATA_MONEY][i]);
            }
        }
        return outString.toString();
    }

    public boolean isDataOk(String date, String dateFrom, String dateTo) {
        return (((LocalDate.parse(date, PATTERN).isBefore(LocalDate.parse(dateTo, PATTERN)))
                || (LocalDate.parse(date, PATTERN).isEqual(LocalDate.parse(dateTo, PATTERN))))
                && ((LocalDate.parse(date, PATTERN).isAfter(LocalDate.parse(dateFrom, PATTERN)))
                || (LocalDate.parse(date, PATTERN).isEqual(LocalDate.parse(dateFrom, PATTERN)))));
    }
}
