package re.sylfa.filterhopper;

import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;

public record DurabilityFilter(DurabilityFilter.Type type, int limit) {
    
    public String serialize() {
        return "" + type.letter + limit;
    }

    public static DurabilityFilter deserialize(String original) {
        if(original.length() < 2) return null;
        Type type = Type.deserialize(original.charAt(0));
        if(type == null) return null;
        
        String limitRaw = original.substring(1);
        if(!NumberUtils.isCreatable(limitRaw)) return null;
        int limit = Integer.parseInt(limitRaw);

        return new DurabilityFilter(type, limit);
    }

    public enum Type {
        ANY('a'),
        EQUALS('E'),
        LESS('l'),
        LESSEQUAL('L'),
        GREATER('g'),
        GREATEREQUAL('G');
        
        char letter;
        Type(char letter) {
            this.letter = letter;
        }

        static Type deserialize(char letter) {
            return Stream.of(Type.values())
                .filter(type -> type.letter == letter)
                .findFirst()
                .orElse(null);
        }
    }
}
