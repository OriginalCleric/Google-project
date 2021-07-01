package com.google;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video currentVideo;
  private HashMap<String, VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlists = new HashMap<>();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    for (Video video: this.videoLibrary.getVideosAsc()){
      System.out.println(video.getDesc());
    }
    //System.out.println("showAllVideos needs implementation");
  }
  public void playVideo(String videoId) {
    Video video = this.getVideo(videoId);
    this.playVideo(video);
  }

  public void playVideo(Video video) {
    if (video == null){
      System.out.println("Cannot play video: Video does not exist");
    }else{
      if (this.currentVideo != null){
        this.stopVideo();
      }
      video.play();
      this.currentVideo = video;
    }
  }

  public void stopVideo() {
    if (this.currentVideo == null){
      System.out.println("Cannot stop video: No video is currently playing");
    }else{
      this.currentVideo.stop();
      this.currentVideo = null;
    }
    //System.out.println("stopVideo needs implementation");
  }

  public void playRandomVideo() {
    //get random video
    Video randomVideo = this.videoLibrary.getRandomVideo();
    if (randomVideo==null || randomVideo.isFlagged()){
      System.out.println("No videos available");
    }else{
      this.playVideo(randomVideo);
    }
  }

  public void pauseVideo() {
    if (this.currentVideo == null){
      System.out.println("Cannot pause video: No video is currently playing");
    }else{
      this.currentVideo.pause();
    }
  }

  public void continueVideo() {
    if (this.currentVideo == null){
      System.out.println("Cannot continue video: No video is currently playing");
    }else{
      this.currentVideo.cont();
    }
  }

  public void showPlaying() {
    if (this.currentVideo==null || this.currentVideo.isFlagged()){
      System.out.println("No video is currently playing");
    }else{
      this.currentVideo.show();
    }
  }

  public void createPlaylist(String playlistName) {
    if (this.playlists.containsKey(playlistName.toLowerCase())){
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }else{
      this.playlists.put(playlistName.toLowerCase(), new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: "+playlistName);
    }
  }

  private VideoPlaylist getPlaylist(String name){
    return (VideoPlaylist) this.playlists.get(name.toLowerCase());
  }

  private Video getVideo(String videoId){
    return this.videoLibrary.getVideo(videoId);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    String errorMessage = "Cannot add video to "+playlistName+": ";
    VideoPlaylist playlist = this.getPlaylist(playlistName);
    Video video = this.getVideo(videoId);
    if (playlist==null){
      System.out.println(errorMessage+"Playlist does not exist");
    }else if (video == null){
      System.out.println(errorMessage+"Video does not exist");
    }else if (playlist.addVideo(video)){
      System.out.println("Added video to "+playlistName+": "+video.getTitle());
    }else if (video.isFlagged()){
      System.out.println(errorMessage+video.getFlagMessage());
    }else{
      System.out.println(errorMessage+"Video already added");
    }
  }

  public void showAllPlaylists() {
    if (this.playlists.isEmpty()){
      System.out.println("No playlists exist yet");
    }else{
      System.out.println("Showing all playlists:");
      for (VideoPlaylist playlist : this.playlists.values()) {
          playlist.print();
      }
    }
  }

  public void showPlaylist(String playlistName) {
    VideoPlaylist playlist = this.getPlaylist(playlistName);
    if (playlist==null){
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
    }else{
      System.out.println("Showing playlist: "+playlistName);
      playlist.show();
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    Video video = this.getVideo(videoId);
    VideoPlaylist playlist = this.getPlaylist(playlistName);
    String errorMessage = "Cannot remove video from "+playlistName+": ";

    if (playlist == null){
      System.out.println(errorMessage+"Playlist does not exist");
    }else if (video==null){
      System.out.println(errorMessage+"Video does not exist");
    }else if (playlist.removeVideo(videoId)){
      System.out.println("Removed video from "+playlistName+": "+video.getTitle());
    }else{
      System.out.println(errorMessage+"Video is not in playlist");
    }
  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist playlist = this.getPlaylist(playlistName);
    if (playlist == null){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
    }else{
      playlist.clear();
      System.out.println("Successfully removed all videos from "+playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    if (this.playlists.remove(playlistName.toLowerCase())==null){
        System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
    }else{
      System.out.println("Deleted playlist: "+playlistName);
    }
  }

  private void playSearchResult(ArrayList<Video> videos){
    int num = 1;
    for (Video video: videos){
      System.out.println(Integer.toString(num++)+") "+video.getDesc());
    }

    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    var scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    int index;
    try {
     index = Integer.parseInt(input);
    }
    catch (NumberFormatException e)
    {
       index = -1;
    }
    if (index>=0 && index<num){
      Video video = videos.get(index-1);
      this.playVideo(video);
    }
  }

  public void searchVideos(String searchTerm) {
    ArrayList<Video> videos = new ArrayList<Video>();
    for (Video video: this.videoLibrary.getVideosAsc()){
      if (video.isFlagged()==false && video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
        videos.add(video);
      }
    }

    if (videos.size()==0){
      System.out.println("No search results for "+searchTerm);
    }else{
      System.out.println("Here are the results for "+searchTerm+":");
      this.playSearchResult(videos);
    }
  }

  public void searchVideosWithTag(String videoTag) {
    ArrayList<Video> videos = new ArrayList<Video>();
    for (Video video: this.videoLibrary.getVideosAsc()){
      if (video.isFlagged()==false && video.hasTag(videoTag)){
        videos.add(video);
      }
    }

    if (videos.size()==0){
      System.out.println("No search results for "+videoTag);
    }else{
      System.out.println("Here are the results for "+videoTag+":");
      this.playSearchResult(videos);
    }
  }

  public void flagVideo(String videoId) {
    this.flagVideo(videoId,"Not supplied");
  }

  public void flagVideo(String videoId, String reason) {
    Video video = this.getVideo(videoId);
    String errorMessage = "Cannot flag video: ";

    if (video == null){
      System.out.println(errorMessage+"Video does not exist");
    }else if (video.isFlagged()){
      System.out.println(errorMessage+"Video is already flagged");
    }else{
      if (this.currentVideo!=null && this.currentVideo.getVideoId().equals(videoId)){
        this.stopVideo();
      }
      video.setFlag(true,reason);
      System.out.println("Successfully flagged video: "+video.getTitle()+" (reason: "+reason+")");
    }
  }

  public void allowVideo(String videoId) {
    Video video = this.getVideo(videoId);
    String errorMessage = "Cannot remove flag from video: ";

    if (video==null){
      System.out.println(errorMessage+"Video does not exist");
    }else if (video.isFlagged()){
      video.setFlag(false,"");
      System.out.println("Successfully removed flag from video: "+video.getTitle());
    }else{
      System.out.println(errorMessage+"Video is not flagged");
    }
  }
}
