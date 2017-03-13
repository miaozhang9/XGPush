package com.example.miaoz.xgpush.network2;



import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * use to extend the converter type
 */
public final class JsonStringConverterFactory extends Converter.Factory {
    private final Converter.Factory delegateFactory;

    public JsonStringConverterFactory(Converter.Factory delegateFactory) {
        this.delegateFactory = delegateFactory;
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Json) {
                Converter<?, RequestBody> delegate =
                        delegateFactory.requestBodyConverter(type, annotations, new Annotation[0], retrofit);
                return new DelegateToStringConverter<>(delegate);
            }
        }
        return null;
    }

    static class DelegateToStringConverter<T> implements Converter<T, String> {
        private final Converter<T, RequestBody> delegate;

        DelegateToStringConverter(Converter<T, RequestBody> delegate) {
            this.delegate = delegate;
        }

        @Override
        public String convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            delegate.convert(value).writeTo(buffer);
            return buffer.readUtf8();
        }
    }
}
