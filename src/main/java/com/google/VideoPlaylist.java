package com.google;
import java.util.LinkedHashMap;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable<VideoPlaylist> {
  private String name;
  private LinkedHashMap<String, Video> videos;

  VideoPlaylist(String name){
    this.videos = new LinkedHashMap<>();
    this.name=name;
  }

  public Boolean addVideo(Video video){
    if (video.isFlagged()){
      return false;
    }
    if (videos.containsKey(video.getVideoId())){
      return false;
    }
    videos.put(video.getVideoId(),video);
    return true;
  }

  public Boolean removeVideo(String videoID){
    if (videos.remove(videoID)==null){
      return false;
    }
    return true;
  }

  public String getName(){
    return this.name.toLowerCase();
  }

  public void print(){
    System.out.println(this.name);
  }

  public void show(){
    if (this.videos.isEmpty()){
      System.out.println("No videos here yet");
    }else{
      for (Video video : this.videos.values()) {
        System.out.println(video.getDesc());
      }
    }
  }

  public void clear(){
    this.videos.clear();
  }
  public int compareTo(VideoPlaylist playlist){
    return this.name.toLowerCase().compareTo(playlist.getName());
  }

}
