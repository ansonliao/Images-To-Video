# Images-To-Video

This is the Java base library generate video from images by [JCodec](http://jcodec.org/).

## Requirements
- Java: Java 8 and or up


## How To
Now the latest version: `0.2`

### Maven
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

```xml
<dependency>
  <groupId>com.github.ansonliao</groupId>
  <artifactId>Images-To-Video</artifactId>
  <version>LATEST_VERSION</version>
</dependency>
```

### Gradle
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.ansonliao:Images-To-Video:Tag'
}
```
## Simple usage: 

Create video from image directory:
```java
ImagesToMovie imagesToMovie = new ImagesToMovie();
String imageDir = "src/test/resources";
String videoFileName = "src/test/resources/video.mp4";
imagesToMovie.createVideo(imageDir, videoFileName);
```

Create video from images
```java
ImagesToMovie imagesToMovie = new ImagesToMovie();
ArrayList<String> images = new ArrayList<>();
images.add("src/test/resources/image1.jpeg");
images.add("src/test/resources/image2.jpeg");
images.add("src/test/resources/image3.jpeg");
String videoFileName = "src/test/resources/video.mp4";
imagesToMovie.createVideo(images, videoFileName);
```

## Video Configurations
Video configurations can place `video.properties` under the `resources` of classpath directory.

| Key | Description | Default Value | Separator | Remark |
| --- | ----------- | ------------- | --------- | ------ |
| video.format | The video will be generated | mp4 | N/A | N/A |
| image.supported.formats| The image supported formats list | jpeg, jpg, png | Comma (,) | If you need to specified your image format, it will overwrite the default's, for example only support `png` and `jpeg`: image.supported.formats=png,jpeg |
| video.fps | The FPS of the generated video | 3 | N/A | N/A |


## License
![Apache License version 2.0](http://www.apache.org/img/asf_logo.png)

Selenium-Extensions is released under [Apache License version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
