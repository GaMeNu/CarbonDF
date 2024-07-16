package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.etc.CarbonTypeEnum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TagOption implements CarbonTypeEnum {
    String id;
    String codeName;
    TagType type;

    public static Map<TagType, Map<String, TagOption>> tagOptionMap = new HashMap<>();

    public static Map<TagType, Map<String, TagOption>> getTagOptionMap() {
        return tagOptionMap;
    }

    static {

        for (Map.Entry<ActionType, JSONArray> entry: TagUtils.getTagMap().entrySet()) {
            ActionType action = entry.getKey();
            JSONArray tagsArr = entry.getValue();
            for (Object obj: tagsArr){
                JSONObject tag = (JSONObject) obj;
                String tagTypeName = TagUtils.tagCodeName(tag.getString("name"));
                TagType type = TagType.getTagType(action, tagTypeName);
                tagOptionMap.put(type, new HashMap<>());

                for (Object o: tag.getJSONArray("options")){
                    JSONObject option = (JSONObject) o;
                    String id = option.getString("name");
                    String codeName = TagUtils.tagCodeName(id);
                    tagOptionMap.get(type).put(codeName, new TagOption(id, codeName, type));

                }

                String defaultCodeName = TagUtils.tagCodeName(tag.getString("defaultOption"));
                TagOption defaultOption = tagOptionMap.get(type).get(defaultCodeName);
                TagType.getTypeMap().get(action).get(tagTypeName).setDefaultOption(defaultOption);
            }
        }
    }

    public static TagOption getTagOption(TagType tagType, String codeName){
        return tagOptionMap.get(tagType).get(codeName);
    }


    TagOption(String id, String codeName, TagType type) {
        this.id = id;
        this.codeName = codeName;
        this.type = type;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getCodeName() {
        return codeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagOption tagOption)) return false;
        return Objects.equals(id, tagOption.id) && Objects.equals(codeName, tagOption.codeName) && Objects.equals(type, tagOption.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeName, type);
    }

    @Override
    public String toString() {
        return String.format("TagOption<%s, %s, %s>", id, codeName, type);
    }
}
