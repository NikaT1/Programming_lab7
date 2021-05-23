package server.collectionUtils;

import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * Интерфейс для классов, которые осуществляют парсинг.
 */
public interface ParserInterface {

    void parseFile(InputStreamReader lines) throws ParseException, NumberFormatException;
}
