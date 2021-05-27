package com.genesis.cryptowallet.util;

import com.genesis.cryptowallet.dto.FileDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class read CSV File
 */
public class ReadCsvFile {

    private static final char SEPARATOR = ',';

    /**
     *
     * @param fileName the file name
     * @return stream of file
     */
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    /**
     *
     * @param fileName the file name
     * @return The list of row
     * @throws IOException Exception if has some problem process read
     */
    public List<FileDTO> readFile(String fileName) throws IOException {
        InputStream is = getFileFromResourceAsStream(fileName);
        List<FileDTO> response = new ArrayList<FileDTO>();
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withDelimiter(SEPARATOR)
                .withHeader()
                .withNullString("")
                .withTrim();

        final BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
        CSVParser csvParser = new CSVParser(bufferReader, csvFormat);
        try {
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord row: records) {
                FileDTO line = new FileDTO();
                line.setSymbol(row.get(0));
                line.setQuantity(new BigDecimal(row.get(1)));
                line.setPrice(new BigDecimal(row.get(2)));
                response.add(line);
            }
        } catch (IOException e) {
            throw new InterruptedIOException("Find some problem read process at the file: " + fileName);
        }

        return response;
    }
}
