package gameRole;

/**
 * One char correspond to one {@code GameObject}
 * @author Lekang Jiang - Modified
 */
public enum GameObject {
    WALL('W'),
    FLOOR(' '),
    CRATE('C'),
    DIAMOND('D'),
    KEEPER('S'),
    KEEPER_UP('U'),
    KEEPER_DOWN('N'),
    KEEPER_LEFT('L'),
    KEEPER_RIGHT('R'),
    CRATE_ON_DIAMOND('O'),
    DEBUG_OBJECT('=');

    private final char symbol;

    /**
     * Constructor
     * <p> Take one char and initialise class variable
     *
     */
    GameObject(final char symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Getter for {@code symbol}
     * @return char
     */
    public char getCharSymbol() {
        return symbol;
    }

    /**
     * Change one char into corresponding {@code GameObject}
     * @param char 
     *
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }
    
}