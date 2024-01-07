package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.toJSONObject;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import org.json.JSONObject;

import java.util.HashMap;

public class EventBlock extends CodeBlock implements toJSONObject{

    public EventBlock(BlockTypes.Type blockType, ActionTypes.Type actionType) {
        super(blockType, actionType);
    }

    public static HashMap<String, EventBlock> eventIDs = new HashMap<>(){{
        put("onPlayerJoin", new EventBlock(BlockTypes.Type.EVENT_PLAYER, ActionTypes.Type.EVENT_JOIN));
        put("onEntityDamageEntity", new EventBlock(BlockTypes.Type.EVENT_ENTITY, ActionTypes.Type.EVENT_ENTITY_DAMAGE_ENTITY));
    }};

    public EventBlock(EventBlock other){
        super(other.blockType, other.actionType);
    }

    public static EventBlock fromID(String id) {
        EventBlock toCopy = eventIDs.getOrDefault(id, null);

        if (toCopy == null){
            throw new RuntimeException(new UnknownEventException(id));
        }

        return new EventBlock(toCopy);
    }

    @Override
    public JSONObject toJSON() {
        return super.toJSON();
    }
}
