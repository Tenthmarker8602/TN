package me.tenth8602.tN;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;

import java.util.Arrays;

import static java.lang.System.in;

public class testExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Audience sender = (Audience) commandSender;
        MiniMessage mm = MiniMessage.miniMessage();
        String txt = String.join(" ", strings);
        Component parsed = mm.deserialize(txt);
        sender.sendMessage(parsed);
        return true;
    }
}
