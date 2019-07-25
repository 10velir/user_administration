package com.useradministration.webapp.mapper;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.setField;

@Slf4j
public class Mapper {

    public static Object MapDtoToEntity(Object from, Object to, List<String> exclude, String... mapParams) {
        Stream.of(from.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> exclude == null || !exclude.contains(field.getName()))
                .filter(field -> getField(field, from) != null)
                .forEach(field -> {
                    String name = field.getName();
                    Field toField = findField(to.getClass(), name);
                    if (toField == null ) {
                        Optional<String> associasion = findAssociasion(name, mapParams);
                        if (associasion.isPresent()) {
                            toField = findField(to.getClass(), associasion.get());
                            if (toField != null) {
                                toField.setAccessible(true);
                            } else {
                                log.warn("field with name \"" + associasion.get() + "\" not found in class: " + to.getClass().getSimpleName());
                            }
                        }
                    } else {
                        toField.setAccessible(true);
                    }
                    if (toField != null) {
                        setField(toField, to, getField(field, from));
                    }
                });
        return to;
    }

    private static Optional<String> findAssociasion(String findField, String... strings) {
        return Stream.of(strings)
                .map(s -> s.split("->"))
                .filter(strings1 -> strings1[0].equals(findField))
                .map(strings1 -> strings1[1])
                .findFirst();
    }
}
