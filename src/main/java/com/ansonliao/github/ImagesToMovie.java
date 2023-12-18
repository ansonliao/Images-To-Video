package com.ansonliao.github;

import com.ansonliao.github.utils.ImageUtils;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ansonliao.github.Configurations.getVideoConfigs;
import static com.ansonliao.github.utils.ExceptionUtils.checkFileExistedOrNot;
import static com.ansonliao.github.utils.ImageUtils.getImagesFromDirectory;
import static com.ansonliao.github.utils.ImageUtils.getTheMaxResolutionOfImage;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

public class ImagesToMovie {
    private static final Logger logger = LoggerFactory.getLogger(ImagesToMovie.class);

    public void createVideo(String imageDir, boolean isReversed, String videoFileName) throws IOException {
        Stream<String> streamImages = getImagesFromDirectory(imageDir)
                .parallelStream()
                .map(File::getAbsolutePath);
        ArrayList<String> images = isReversed
                ? streamImages.sorted(Comparator.reverseOrder()).collect(toCollection(ArrayList::new))
                : streamImages.sorted().collect(toCollection(ArrayList::new));
        convertJPGtoMovie(images, videoFileName);
    }

    public void createVideo(List<String> images, String videoFileName) throws IOException {
        convertJPGtoMovie(images, videoFileName);
    }

    protected void convertJPGtoMovie(List<String> links, String videoFileName) throws IOException {
        if (links.isEmpty()) {
            logger.info("No images provided to create video.");
            return;
        }
        logger.info("========== Generate Video From Images ==========");
        logger.info("Check all the images existing...");
        for (String link : links) {
            checkFileExistedOrNot(link).throwMessage("The image is not existed, path:  " + link);
        }
        logger.info("Video fill will be created: {}", videoFileName);
        links.forEach(link -> logger.info("Image: {}", link));
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();

        Map<ImageUtils.ImageResolution, Integer> resolution = getTheMaxResolutionOfImage(
                links.stream().map(File::new).collect(toList()));
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                videoFileName,
                resolution.get(ImageUtils.ImageResolution.HEIGHT),
                resolution.get(ImageUtils.ImageResolution.WIDTH));
        try {
            recorder.setFrameRate(getVideoConfigs().videoFrameRate());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setVideoBitrate(getVideoConfigs().videoBitRate());
            recorder.setFormat(getVideoConfigs().videoFormat());
            recorder.setVideoQuality(getVideoConfigs().videoQuality());    // 0 is the maximum quality
            recorder.start();
            for (String link : links) {
                recorder.record(grabberConverter.convert(cvLoadImage(link)));
            }
            recorder.stop();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            recorder.close();
            grabberConverter.close();
        }

        logger.info("\nVideo has been created at {}", videoFileName);
        logger.info("=========== Generate Video Completed ===========");
    }
}
