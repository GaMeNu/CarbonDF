package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.toJSONObject;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import org.json.JSONObject;

import java.util.HashMap;

public class EventBlock extends CodeBlock implements toJSONObject{

    public EventBlock(BlockType blockType, ActionType actionType) {
        super(blockType, actionType);
    }

    public static HashMap<String, EventBlock> eventIDs = new HashMap<>(){{
        for (ActionType at: ActionType.getAllOfBlock(BlockType.fromID("event")).values()) {
            put(at.getCodeName(), new EventBlock(at.getBlock(), at));
        }
        for (ActionType at: ActionType.getAllOfBlock(BlockType.fromID("entity_event")).values()) {
            put(at.getCodeName(), new EventBlock(at.getBlock(), at));
        }
    }};

    public EventBlock(EventBlock other){
        super(other.blockType, other.actionType);
    }

    public static EventBlock fromID(String id) {
        EventBlock toCopy = eventIDs.getOrDefault(id, null);

        if (toCopy == null){
            throw new UnknownEventException(id);
        }

        return new EventBlock(toCopy);
    }

    @Override
    public JSONObject toJSON() {
        return super.toJSON();
    }
}
