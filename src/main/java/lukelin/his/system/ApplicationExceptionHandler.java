package lukelin.his.system;

import lukelin.common.springboot.exception.ApiValidationException;
import lukelin.common.springboot.handler.BaseExceptionHandler;
import lukelin.common.util.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class ApplicationExceptionHandler extends BaseExceptionHandler {
    public ResponseEntity handleHttpStatusCodeException(HttpStatusCodeException e) {
        String errorString =new String(e.getResponseBodyAsByteArray(), StandardCharsets.UTF_8);
        return !StringUtils.isEmpty(e.getResponseBodyAsString()) ?
                new ResponseEntity(JsonUtils.fromJsonString(errorString), e.getStatusCode()) : new ResponseEntity(this.processMessage(e.getStatusText()), e.getStatusCode());

    }
}
