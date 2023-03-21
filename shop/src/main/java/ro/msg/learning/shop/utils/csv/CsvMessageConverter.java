package ro.msg.learning.shop.utils.csv;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.TypeFactory.rawClass;

@Component
public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    @Autowired
    private CsvUtils csvUtils;

    public CsvMessageConverter(){
        super(new MediaType("text", "csv"));
    }

    @Override
    protected void writeInternal(@NonNull List<T> pojos, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Class<?> cls = rawClass(((ParameterizedType) type).getActualTypeArguments()[0]);
        csvUtils.toCsv(cls,pojos,outputMessage.getBody());
    }

    @Override
    protected List<T> readInternal(@NonNull Class<? extends List<T>> clazz,@NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return new ArrayList<>();
    }

    @Override
    public List<T> read(@NonNull Type type, Class<?> contextClass,@NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return new ArrayList<>();
    }

}
