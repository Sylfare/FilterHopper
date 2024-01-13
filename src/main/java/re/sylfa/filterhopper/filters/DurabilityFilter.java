package re.sylfa.filterhopper.filters;

import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

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

    public boolean checks(ItemStack item){
        
        // if the item does not have durability, don't check it
        boolean hasDurability = item.getType().getMaxDurability() != 0;
        if (!hasDurability) return type == Type.ANY;

        int itemDurability = item.getType().getMaxDurability() - ((Damageable)item.getItemMeta()).getDamage();

        return switch (this.type) {
            case ANY          -> true;
            case EQUALS       -> itemDurability == this.limit;
            case LESS         -> itemDurability <  this.limit;
            case LESSEQUAL    -> itemDurability <= this.limit;
            case GREATER      -> itemDurability >  this.limit;
            case GREATEREQUAL -> itemDurability >= this.limit;
        };
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
