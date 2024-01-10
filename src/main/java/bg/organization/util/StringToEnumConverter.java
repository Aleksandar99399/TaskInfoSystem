package bg.organization.util;

import bg.organization.models.Position;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

public class StringToEnumConverter implements Converter<String, Position> {
    @Override
    public Position convert(MappingContext<String, Position> context) {
        return Optional.ofNullable(context.getSource())
                .map(String::toUpperCase)
                .map(Position::valueOf)
                .orElse(null);
    }
}