package fooding.im.core.domain.reward;

public enum RewardType {
    EVENT("EVENT"),
    VISIT("VISIT");

    private String name;

    private RewardType( String name ) { this.name = name; }

    public String getName() { return this.name; }

    public static RewardType fromString( String name ){
        for ( RewardType type: values() ){
            if( type.name.equalsIgnoreCase(name) ) return type;
        }
        throw new IllegalArgumentException();
    }

}
