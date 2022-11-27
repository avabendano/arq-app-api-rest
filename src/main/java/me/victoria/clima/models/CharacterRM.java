package me.victoria.clima.models;

/**
 *
 * @author Victoria
 */
public class CharacterRM {
    public int id;
    public String name;
    public String species;
    public String image;
    
    public CharacterRM(int id, String name, String species, String image) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.image = image;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d - %s | %s", this.id, this.name, this.species);
    }
}
