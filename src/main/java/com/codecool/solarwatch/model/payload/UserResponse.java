package com.codecool.solarwatch.model.payload;

import java.util.List;

public record UserResponse(Long id, String userName, List<String> roles) {
}
