package org.sergheimorari.letterprocessor.exceptionhandling;

import java.util.List;
import java.util.Map;

public record ErrorResponse(String message, List<Map<String, String>> details) {}
