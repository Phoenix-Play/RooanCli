package roo.ancli;

import net.arikia.dev.drpc.*;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    public static final String VERSION = "0.1.0";
    public static final String NAME = "Rooan Client";
    public static final String DESCRIPTION = "A command line client for the Rooan server.";
    public static final String discordRPCToken = "1105641834866286652";
    private static String discordRPCDetails = "Playing on Rooan Minecraft Client";
    private static String discordRPCState = "Main Menu";
    private static String discordRPCImageKey = "main";
    private static boolean discordRPCRunning = false;
    private static final Logger LOGGER = LogManager.getLogger();

    public static void Hook() {
        System.out.println("Hello World From RooanClient!");
        Minecraft.getInstance().getMainWindow().setTitle(NAME + " " + VERSION + " - " + DESCRIPTION);

        // Starts RPC
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
                    discordRPCRunning = true;
                    DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder(discordRPCState);
                    presence.setBigImage(discordRPCImageKey, discordRPCDetails);
                    presence.setDetails(discordRPCDetails);
                    DiscordRPC.discordUpdatePresence(presence.build());

                    LOGGER.info("Discord Rich Presence is now running.");
                }).setErroredEventHandler((errorCode, message) -> LOGGER.error("Discord Rich Presence has errored: " + message))
                .setDisconnectedEventHandler((errorCode, message) -> LOGGER.warn("Discord Rich Presence has disconnected: " + message))
                .build();

        DiscordRPC.discordInitialize(discordRPCToken, handlers, true);
        DiscordRPC.discordRegister(discordRPCToken, "");
    }

    public static void Loop() {
        // Runs RPC
        DiscordRPC.discordRunCallbacks();

        if (discordRPCRunning) {
            DiscordRichPresence.Builder presence = new DiscordRichPresence.Builder(discordRPCState);
            presence.setBigImage(discordRPCImageKey, discordRPCDetails);
            presence.setDetails(discordRPCDetails);
            DiscordRPC.discordUpdatePresence(presence.build());
        }
    }
}
