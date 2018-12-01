package com.demos.caching.redismemcache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
class WriteKeyRequest {

    @NonNull
    @Max(256)
    private String key;

    @NotNull
    private String value;

    private int expiresInSeconds;

    public boolean hasToExpire() {
        return expiresInSeconds > 0;
    }
}
