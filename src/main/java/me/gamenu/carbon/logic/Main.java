package me.gamenu.carbon.logic;

import me.gamenu.carbon.logic.args.TagOption;
import me.gamenu.carbon.logic.compile.Compile;

public class Main {

    public static void main(String[] args) {
        TagOption.getTagOptionMap();
        Compile.fromFile("src/test/carbon_df_files/test.dfc");
    }
}
