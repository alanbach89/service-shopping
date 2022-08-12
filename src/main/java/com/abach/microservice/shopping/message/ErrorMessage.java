package com.abach.microservice.shopping.message;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {

    private String code;
    private List<Map<String, String>> messages;
}
