package fooding.im.core.domain.reward;

public enum RewardChannel {
    STORE("STORE"),
    EVENT_PLATFORM("EVENT_PLATFORM");

    private String name;

    private RewardChannel( String name ){ this.name = name; }
    public String getName() { return this.name; }

    public static RewardChannel fromString( String name ){
        for ( RewardChannel channel: values() ){
            if( channel.name.equalsIgnoreCase(name) ) return channel;
        }
        throw new IllegalArgumentException();
    }
}

