package me.deployable.prison;

import me.deployable.prison.commands.rankup;
import me.deployable.prison.commands.upgrade;
import me.deployable.prison.events.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prison extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("ranks").setExecutor(new rankup(this));
        getCommand("rankup").setExecutor(new upgrade(this));
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);


    }

}

