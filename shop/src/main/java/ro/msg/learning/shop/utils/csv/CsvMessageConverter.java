package ro.msg.learning.shop.utils.csv;

//
//@Component
//public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {
//
//    @Autowired
//    private CsvUtils csvUtils;
//
//    public CsvMessageConverter(){
//        super(new MediaType("text", "csv"));
//    }
//
//    @Override
//    protected void writeInternal(@NonNull List<T> pojos, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        Class<?> cls = rawClass(((ParameterizedType) type).getActualTypeArguments()[0]);
//        csvUtils.toCsv(cls,pojos,outputMessage.getBody());
//    }
//
//    @Override
//    protected List<T> readInternal(@NonNull Class<? extends List<T>> clazz,@NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//        return new ArrayList<>();
//    }
//
//    @Override
//    public List<T> read(@NonNull Type type, Class<?> contextClass,@NonNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//        return new ArrayList<>();
//    }
//
//}
