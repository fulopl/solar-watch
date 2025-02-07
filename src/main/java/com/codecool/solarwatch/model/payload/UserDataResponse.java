package com.codecool.solarwatch.model.payload;

import java.util.List;

public record UserDataResponse(String userName, List<String> roles) {
}
