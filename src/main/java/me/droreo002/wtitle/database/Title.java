package me.droreo002.wtitle.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.droreo002.oreocore.database.object.DatabaseFlatFile;
import me.droreo002.oreocore.title.OreoTitle;
import me.droreo002.oreocore.title.TitleAnimation;
import me.droreo002.oreocore.title.TitleFrame;
import me.droreo002.oreocore.utils.misc.SoundObject;
import me.droreo002.oreocore.utils.strings.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Title {

    @Getter
    private final FileConfiguration data;
    @Getter
    private String region;
    @Getter
    private String name;
    @Getter
    private OreoTitle oreoTitle;
    @Getter
    private boolean playSound;
    @Getter
    private SoundObject titleSound;
    @Getter
    private boolean useAnimation;
    @Getter
    private List<TitleFrame> animationFrame;
    @Getter
    private int animationSpeed;
    @Getter
    private boolean sendMessage;
    @Getter
    private String message;
    @Getter
    private TitleType titleType;

    public Title(String name, FileConfiguration data) {
        this.data = data;
        this.name = name;
        this.region = data.getString("Data.region");
        this.oreoTitle = OreoTitle.deserialize(data.getConfigurationSection("Data.title"));
        this.playSound = data.getBoolean("Data.playSound");
        this.titleSound = SoundObject.deserialize(data.getConfigurationSection("Data.sound"));
        this.useAnimation = data.getBoolean("Data.useAnimation");
        this.animationFrame = new ArrayList<>();
        this.animationSpeed = data.getInt("Data.animationSpeed");
        this.sendMessage = data.getBoolean("Data.sendMessage");
        this.message = data.getString("Data.message");
        this.titleType = TitleType.valueOf(data.getString("Data.titleType"));

        if (useAnimation) {
            this.oreoTitle.setFadeIn(0);
            this.oreoTitle.setFadeOut(0);
            final List<String> animationAsString = data.getStringList("Data.animation");
            for (String s : animationAsString) {
                final String[] split = s.split("<\\|>");
                this.animationFrame.add(new TitleFrame() {
                    @Override
                    public String getNextTitle(String prevTitle) {
                        return split[0];
                    }

                    @Override
                    public String getNextSubTitle(String prevSubTitle) {
                        return split[1];
                    }
                });
            }
        }
    }

    /**
     * Send the title to the player
     *
     * @param player The target player
     */
    public void sendTitle(Player player) {
        if (sendMessage) player.sendMessage(StringUtils.color(message));
        if (playSound) titleSound.send(player);

        if (useAnimation) {
            TitleAnimation animation = new TitleAnimation(oreoTitle, animationSpeed);
            animation.setRepeatingAnimation(false);
            this.animationFrame.forEach(animation::addFrame);
            animation.start(player);
        } else {
            oreoTitle.send(player);
        }
    }
}
