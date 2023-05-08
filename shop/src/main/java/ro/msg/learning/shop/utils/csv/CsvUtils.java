package ro.msg.learning.shop.utils.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class CsvUtils {

    private CsvMapper csvMapper = new CsvMapper();

    public <T> List<T> fromCsv(Class<T> clazz, InputStream inputStream) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(clazz);
        MappingIterator<T> it = csvMapper.readerWithSchemaFor(clazz).with(schema).readValues(inputStream);
        return it.readAll();
    }

    public <T> void toCsv(Class<?> clazz, List<T> pojos, OutputStream outputStream) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
        csvMapper.writer(schema).writeValue(outputStream, pojos);
    }
}
