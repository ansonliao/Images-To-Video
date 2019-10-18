package com.ansonliao.github;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static com.ansonliao.github.Configurations.getVideoConfigs;
import static com.ansonliao.github.utils.ImageUtils.isSupportedImageFormat;
import static java.util.stream.Collectors.toCollection;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

public class ImagesToMovie {
    private static final Logger logger = LoggerFactory.getLogger(ImagesToMovie.class);

    public void createVideo(String imageDir, boolean isReversed, String videoFileName) {
        Stream<String> streamImages = getImages(imageDir)
                .parallelStream()
                .map(File::getAbsolutePath);
        ArrayList<String> images = isReversed
                ? streamImages.sorted(Comparator.reverseOrder()).collect(toCollection(ArrayList::new))
                : streamImages.sorted().collect(toCollection(ArrayList::new));
        convertJPGtoMovie(images, videoFileName);
    }

    public void createVideo(ArrayList<String> images, String videoFileName) {
        convertJPGtoMovie(images, videoFileName);
    }

    protected void convertJPGtoMovie(ArrayList<String> links, String videoFileName) {
        if (links.isEmpty() || links.size() == 0) {
            logger.info("No images provided to create video.");
            return;
        }
        logger.info("========== Generate Video From Images ==========");
        logger.info("Video fill will be created: {}", videoFileName);
        links.forEach(link -> logger.info("Image: {}", link));
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(videoFileName, getVideoConfigs().imageWidth(), getVideoConfigs().imageHeight());
        try {
            recorder.setFrameRate(getVideoConfigs().videoFrameRate());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setVideoBitrate(getVideoConfigs().videoBitRate());
            recorder.setFormat(getVideoConfigs().videoFormat());
            recorder.setVideoQuality(getVideoConfigs().videoQuality());    // 0 is the maximum quality
            recorder.start();
            for (int i = 0; i < links.size(); i++) {
                recorder.record(grabberConverter.convert(cvLoadImage(links.get(i))));
            }
            recorder.stop();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
        }

        logger.info("\nVideo has been created at {}", videoFileName);
        logger.info("================================================");
    }

    public ArrayList<File> getImages(String imageDir) {
        ArrayList<File> images = new ArrayList<>();
        File f = new File(imageDir);
        Arrays.stream(f.listFiles())
                .filter(file -> isSupportedImageFormat(file.getName()))
                .forEach(images::add);
        return images;
    }

}
