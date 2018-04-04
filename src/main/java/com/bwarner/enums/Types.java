package com.bwarner.enums;

public enum Types {

    CSS("CSS"),
    GIF("GIF"),
    HTM("HTM"),
    HTML("HTML"),
    ICO("ICO"),
    JPG("JPG"),
    JPEG("JPEG"),
    JS("JS"),
    JSON("JSON"),
    PNG("PNG"),
    TXT("TXT"),
    XML("XML");

    private final String extension;

    Types(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        switch (this) {
            case CSS:
                return "Content-Type: text/css";
            case GIF:
                return "Content-Type: image/gif";
            case HTM:
                return "Content-Type: text/html";
            case HTML:
                return "Content-Type: text/html";
            case ICO:
                return "Content-Type: image/gif";
            case JPG:
                return "Content-Type: image/jpeg";
            case JPEG:
                return "Content-Type: image/jpeg";
            case JS:
                return "Content-Type: application/javascript";
            case JSON:
                return "Content-Type: application/json";
            case PNG:
                return "Content-Type: image/png";
            case TXT:
                return "Content-type: text/plain";
            case XML:
                return "Content-type: application/xml";
            default:
                return null;
        }
    }

}
