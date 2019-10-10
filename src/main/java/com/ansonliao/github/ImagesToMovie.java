package com.ansonliao.github;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.toCollection;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvLoadImage;

public class ImagesToMovie {
    private static final Logger logger = LoggerFactory.getLogger(ImagesToMovie.class);
    private static final String VIDEO_FORMAT = "mp4";
    private static final int IMAGE_WIDTH = 640;
    private static final int IMAGE_Height = 720;
    private static final int VIDEO_MAX_QUALITY = 0;

    public void createVideo(String imageDir, String videoFileName) {
        ArrayList<String> images = getImages(imageDir)
                .parallelStream()
                .map(File::getAbsolutePath)
                .sorted()
                .collect(toCollection(ArrayList::new));
        convertJPGtoMovie(images, videoFileName);
    }

    public void createVideo(String imageDir, String imagePrefix, String videoFileName) {
        ArrayList<File> allImages = getImages(imageDir);
        ArrayList<String> images = allImages
                .parallelStream()

                .filter(file -> {
                    String fileName = file.getName();
                    return fileName.startsWith(imagePrefix);
                })
                .map(File::getAbsolutePath)
                .sorted()
                .collect(toCollection(ArrayList::new));

        convertJPGtoMovie(images, videoFileName);
    }

    public void convertJPGtoMovie(ArrayList<String> links, String vidPath) {
        // logger.info("Create video from image dir: {}, and save to: {}", imageDir, videoFileName);
        if (links.isEmpty() || links.size() == 0) {
            throw new IllegalArgumentException("No images for creating video");
        }
        links.forEach(link -> logger.info("Image: {}", link));
        logger.info("Video has been created at {}", vidPath);
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath, IMAGE_WIDTH, IMAGE_Height);
        try {
            recorder.setFrameRate(1);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setVideoBitrate(9000);
            recorder.setFormat(VIDEO_FORMAT);
            recorder.setVideoQuality(VIDEO_MAX_QUALITY);    // maximum quality
            recorder.start();
            for (int i = 0; i < links.size(); i++) {
                recorder.record(grabberConverter.convert(cvLoadImage(links.get(i))));
            }
            recorder.stop();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<File> getImages(String imageDir) {
        ArrayList<File> images = new ArrayList<>();
        File f = new File(imageDir);
        Arrays.stream(f.listFiles())
                .filter(file -> {
                    String fileName = file.getName();
                    return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
                })
                .forEach(images::add);
        return images;
    }

    // public static void main(String[] args) {
    //     ImagesToMovie imagesToMovie = new ImagesToMovie();
    //     String path = "/Users/ansonliao/repo/melco/mt-selenium-cucumber-refactor/build/screenshots/chrome/Limo_Booking_Now_Others_Request/Verify_the_others_request's_character_length_feature";
    //     imagesToMovie.getImages(path)
    //             .parallelStream()
    //             .filter(f -> f.startsWith("Verify_the_others_request's_character_length_feature_5_"))
    //             .forEach(System.out::println);
    // }
}
