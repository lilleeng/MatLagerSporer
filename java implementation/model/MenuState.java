package model;
public enum MenuState {
    
    NO_ITEMS,
    //register
    //quit
    //OTHER     (do nothing)

    ITEM_OVERVIEW,

    //sortby [lce | expired | abc | rb] (least content estimate/expired/alphabetically/recently bought)
    //register
    //delete
    //next 
    //prev
    //quit
    //OTHER     (do nothing)

    REGISTRATION_MODE_INPUT_NAME,

    //      (empty takes one back to ITEM_OVERVIEW)

    REGISTRATION_MODE_INPUT_EXPIRATION_DATE,

    //      (empty returns to REGISTRATION_MODE_INPUT_NAME)

    REGISTRATION_MODE_CONFIRMATION,

    //y     (affirmative)
    //n     (negative, returns to REGISTRATION_MODE_INPUT_EXPIRATION_DATE)
    //OTHER     (do nothing)

    REGISTRATION_MODE_COMPLETE,

    //ALL   (return to item overview)

    DELETE_MODE_OVERVIEW,

    //      (empty returns to ITEM_OVERVIEW)
    //[0-9]     (select item from overview)
    //next
    //prev

    DELETE_MODE_CONFIRMATION,

    //y     (affirmative)
    //n     (negative, returns to DELETE_MODE)
    //OTHER     (do nothing)

    DELETE_MODE_COMPLETE;

    //ALL   (return to item overview)

}