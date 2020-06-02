package net.guides.springboot2.springboot2jpacrudexample.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class APIResponse<T>  {
    private int code;
    private String message;
    private T body;

    public APIResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
