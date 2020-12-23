package fr.xmascraft.tools;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;

public class ClickableBuilder {
    private final TextComponent text; // RENDER
    // CONSTRUCTORS
    public ClickableBuilder(String text) {
        this.text = new TextComponent(text.replace("&", "§"));
    }

    public ClickableBuilder setClickAction(ClickEvent.Action action, String value) {
        text.setClickEvent(new ClickEvent(action, value));
        return this;
    }
    public ClickableBuilder setHoverAction(HoverEvent.Action action, String value) {
        text.setHoverEvent(new HoverEvent(action, new ComponentBuilder(value.replace("&", "§")).create()));
        return this;
    }
    public ClickableBuilder addExtra(Object extra) {
        if (extra instanceof String) text.addExtra((String) extra);
        else if (extra instanceof TextComponent) text.addExtra((TextComponent) extra);
        return this;
    }
    public ClickableBuilder addExtras(Object... extras) {
        Arrays.asList(extras).forEach(extra -> {
            assert !(extra instanceof String);
            assert !(extra instanceof TextComponent);
            addExtra(extra);
        });
        return this;
    }

    // GETTERS
    public TextComponent build() {
        return text;
    }
}
