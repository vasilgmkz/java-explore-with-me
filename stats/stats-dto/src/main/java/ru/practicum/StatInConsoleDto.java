package ru.practicum;

import lombok.Data;

@Data
public class StatInConsoleDto {
    String app;
    String uri;
    Integer hits;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        StatInConsoleDto guest = (StatInConsoleDto) obj;
        return app.equals(guest.getApp()) && uri.equals(guest.getUri());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((app == null) ? 0 : app.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }
}
