package com.google;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private Boolean pause;
  private Boolean flagged;
  private String reason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.flagged=false;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
    this.pause=false;
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  Boolean hasTag(String tag){
    for (String myTag:this.getTags()){
      if (myTag.toLowerCase().equals(tag.toLowerCase())){
        return true;
      }
    }
    return false;
  }

  String getDesc(){
    String message = this.getTitle()+" ("+this.getVideoId()+") [";

    //adding the tags, could be improved
    for (String tag:this.getTags()){
      message += tag+" ";
    }
    if (this.getTags().size() != 0){
      message = message.substring(0,message.length()-1);
    }
    message +="]";
    if (this.isFlagged()){
      message+=" - FLAGGED (reason: "+this.getReason()+")";
    }
    return message;
  }

  Boolean pause(){
    if (this.pause){
      System.out.println("Video already paused: "+this.getTitle());
      return false;
    }else{
      System.out.println("Pausing video: "+this.getTitle());
      this.pause = true;
      return true;
    }
  }

  Boolean cont(){
    if (this.pause){
      System.out.println("Continuing video: "+this.getTitle());
      this.pause = false;
      return true;
    }else{
      System.out.println("Cannot continue video: Video is not paused");
      return false;
    }
  }

  String getReason(){
    return this.reason;
  }

  Boolean isFlagged(){
    return this.flagged;
  }

  String getFlagMessage(){
    return ("Video is currently flagged (reason: "+this.getReason()+")");
  }

  void setFlag(Boolean flag, String reason){
   this.flagged=flag;
   this.reason=reason;
  }

  Boolean play(){
    if (this.isFlagged()){
      System.out.println("Cannot play video: "+this.getFlagMessage());
      return false;
    }else{
      this.pause=false;
      System.out.println("Playing video: "+this.getTitle());
      return true;
    }
  }

  Boolean stop(){
    System.out.println("Stopping video: "+this.getTitle());
    return true;
  }

  Boolean show(){
    String message = "Currently playing: "+this.getDesc();
    if (this.pause){
      message += " - PAUSED";
    }
    System.out.println(message);
    return true;
  }
}
