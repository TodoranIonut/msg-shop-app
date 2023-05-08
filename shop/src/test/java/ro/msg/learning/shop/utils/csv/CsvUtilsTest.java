package ro.msg.learning.shop.utils.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class CsvUtilsTest {

    @Autowired
    CsvUtils csvUtils;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestObject {
        private int field1;
        private String field2;
    }

    @SneakyThrows
    @Test
    void fromCsvTest() {
        //test data
        String csvData = "field1,field2\n1,abc\n2,def\n";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        //call function
        List<TestObject> testObjectList = csvUtils.fromCsv(TestObject.class, inputStream);

        //check result
        assertEquals(2,testObjectList.size());
        assertEquals(1,testObjectList.get(0).getField1());
        assertEquals(2,testObjectList.get(1).getField1());
        assertEquals("abc",testObjectList.get(0).getField2());
        assertEquals("def",testObjectList.get(1).getField2());
    }

    @SneakyThrows
    @Test
    void toCsvTest() {
        //test data
        List<TestObject> testObjectsList = Arrays.asList(
                new TestObject(1, "abc"),
                new TestObject(2, "def")
        );

        //call function
        OutputStream outputStream = new ByteArrayOutputStream();
        csvUtils.toCsv(TestObject.class, testObjectsList, outputStream);

        //check result
        String csvData = outputStream.toString();
        String[] lines = csvData.split("\n");
        assertEquals(3, lines.length);
        assertEquals("field1,field2", lines[0]);
        assertEquals("1,abc", lines[1]);
        assertEquals("2,def", lines[2]);
    }
}