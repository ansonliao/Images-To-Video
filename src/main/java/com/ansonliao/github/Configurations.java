package com.ansonliao.github;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

public class Configurations {
    private static VideoConfigs videoConfigs = ConfigFactory.create(VideoConfigs.class, System.getenv(), System.getProperties());

    public static VideoConfigs getVideoConfigs() {
        return videoConfigs;
    }

    @Config.Sources("classpath:video.properties")
    public interface VideoConfigs extends Config {

        @Key("video.format")
        @DefaultValue("mp4")
        String videoFormat();

        @Key("image.supported.formats")
        @DefaultValue("jpeg, jpg, png")
        @Separator(",")
        List<String> imageSupportedFormats();

        @Key("video.fps")
        @DefaultValue("3")
        int videoFps();

    }
}
