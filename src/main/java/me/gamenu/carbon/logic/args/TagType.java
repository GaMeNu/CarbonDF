package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.etc.CarbonTypeEnum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TagType implements CarbonTypeEnum {

    public static Map<ActionType, Map<String, TagType>> tagTypeMap = new HashMap<>();

    static {

        for (Map.Entry<ActionType, JSONArray> entry: TagUtils.getTagMap().entrySet()) {
            ActionType action = entry.getKey();
            tagTypeMap.put(action, new HashMap<>());
            JSONArray tagsArr = entry.getValue();
            for (Object obj: tagsArr){
                JSONObject tag = (JSONObject) obj;
                String id = tag.getString("name");
                String codeName = TagUtils.tagCodeName(id);
                tagTypeMap.get(action).put(codeName, new TagType(id, codeName, action));
            }
        }
    }

    public static TagType getTagType(ActionType actionType, String codeName){
        return tagTypeMap.get(actionType).get(codeName);
    }

    public static Map<ActionType, Map<String, TagType>> getTypeMap() {
        return tagTypeMap;
    }

    String id;
    String codeName;
    ActionType action;
    TagOption defaultOption;

    TagType(String id, String codeName, ActionType action) {
        this.id = id;
        this.codeName = codeName;
        this.action = action;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getCodeName() {
        return codeName;
    }

    public ActionType getAction() {
        return action;
    }

    public TagType setDefaultOption(TagOption defaultOption) {
        this.defaultOption = defaultOption;
        return this;
    }

    public TagOption getDefaultOption() {
        return defaultOption;
    }

    public TagOption getOption(String codeName){
        return TagOption.getTagOption(this, codeName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagType tagType)) return false;
        return Objects.equals(id, tagType.id) && Objects.equals(codeName, tagType.codeName) && action == tagType.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeName, action);
    }

    @Override
    public String toString() {
        return String.format("TagType<%s, %s>", codeName, action);
    }
}
