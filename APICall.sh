#!/bin/bash

trimQuotes() {
  #remove "" from the beginning and end of a string.
  echo "${1:1:-1}"
}

returnToken() {
  node fetchToken.js
}

getPlaylistJSON() {
  #$1 = token.
  #$2 = playlist_id.
  URL='https://api.spotify.com/v1/playlists/'$2
  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$1 \
    --header 'Content-Type: application/json' >"API_Datas/PlaylistTempDatas/$2.JSON"
  #

  getCondensedDataFromPlayList "API_Datas/PlaylistTempDatas/$2.JSON" $2
}

getCondensedDataFromPlayList() {
  #$1 = filePath.
  #$2 = playlist_id.
  playlist_name=$(trimQuotes "$(jq .name $1)")
  echo "$playlist_name:$2" >"Condensed_Datas/PlaylistDatas/$2.txt"
  playlist_length=$(jq '.tracks.items | length' $1)
  for ((i = 0; i < $playlist_length; i++)); do
    song_id=$(trimQuotes "$(jq .tracks.items[$i].track.id $1)")
    song_name=$(trimQuotes "$(jq .tracks.items[$i].track.name $1)")
    getTrackJSON $Token $song_id
    echo "$song_name" >>"Condensed_Datas/PlaylistDatas/$playlist_name.txt"
  done
}

getTrackJSON() {
  #1 = token.
  #2 = track_id.
  URL='https://api.spotify.com/v1/tracks/'$2

  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$1 \
    --header 'Content-Type: application/json' > "API_Datas/SongTempDatas/$2.JSON"

  getCondensedDataFromTrack "API_Datas/SongTempDatas/$2.JSON" $2
}

getCondensedDataFromTrack() {
  #$1 = filePath
  #$2 = track_od.
  track_name=$(trimQuotes "$(jq .name $1)")
  
  echo "$track_name:$2" >"Condensed_Datas/SongDatas/$2.txt"
}

main() {
  Token=$(returnToken)
  getPlaylistJSON $Token $playlist_id
}

programname=$0
playlist_id=$1
main

: <<"COMMENT"
typeOfAnalysis='tracks'
URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$1

curl --request GET \
  --url $URL \
  --header 'Authorization: Bearer '$Token \
  --header 'Content-Type: application/json' >"API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON"
#
nameOfTrack=$(jq .name API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON)
nameOfTrack="${nameOfTrack:1:-1}"

#Create the Song file
echo "" >"Condensed_Datas/SongDatas/$nameOfTrack.txt"

#Add the Track name into the Song file
echo 'Track Name: '$nameOfTrack >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"

#Add the Track ID into the Song file
trackID=$(jq .id "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
trackID="${trackID:1:-1}"
echo 'Track ID: '$trackID >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"

#Getting the song Album information
albumID=$(jq .album.id "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
albumID="${albumID:1:-1}"
albumName=$(jq .album.name "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
albumName="${albumName:1:-1}"
URL='https://api.spotify.com/v1/albums/'$albumID
curl --request GET \
  --url $URL \
  --header 'Authorization: Bearer '$Token \
  --header 'Content-Type: application/json' >API_Datas/AlbumsTempDatas/AlbumTemp.JSON

#Create Song Genres var
songGenres=''

#Add all the artists name, ID into this Song file.
artistsLength=$(jq '.artists | length' "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
for ((i = 1; i <= $artistsLength; i++)); do
  indexArr=$i-1
  #Add Artist ID / Name into Song File
  artistName=$(jq .artists[$indexArr].name "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
  artistName="${artistName:1:-1}"
  artistID=$(jq .artists[$indexArr].id "API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON")
  artistID="${artistID:1:-1}"
  echo 'Artist '$i' Name: '$artistName >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"
  echo 'Artist '$i' Id: '$artistID >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"
  #Create Artist File respective to this song.
  echo '' >"API_Datas/ArtistsTempDatas/$artistName.JSON"
  URL='https://api.spotify.com/v1/artists/'$artistID
  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$Token \
    --header 'Content-Type: application/json' >"API_Datas/ArtistsTempDatas/$artistName.JSON"
  #

  #Create the actual data file via a txt file.
  echo '' >"Condensed_Datas/ArtistsDatas/$artistName.txt"
  #Add artist name, genres and stats into a this new txt file.
  echo 'Artist Name: '$artistName >>"Condensed_Datas/ArtistsDatas/$artistName.txt"
  echo 'Artist Popularity: '$(jq .popularity "API_Datas/ArtistsTempDatas/$artistName.JSON") >>"Condensed_Datas/ArtistsDatas/$artistName.txt"
  echo 'Artist total followers: '$(jq .followers.total "API_Datas/ArtistsTempDatas/$artistName.JSON") >>"Condensed_Datas/ArtistsDatas/$artistName.txt"
  genresLength=$(jq '.genres | length' "API_Datas/ArtistsTempDatas/$artistName.JSON")
  allGenres=''
  for ((z = 0; z < $genresLength; z++)); do
    addGenres=$(jq .genres[$z] "API_Datas/ArtistsTempDatas/$artistName.JSON")
    addGenres="${addGenres:1:-1}"
    if [ $z == $((genresLength - 1)) ]; then
      allGenres="$allGenres$addGenres"
    else
      allGenres="$allGenres$addGenres | "
    fi
  done
  echo 'Artist genres: '$allGenres >>"Condensed_Datas/ArtistsDatas/$artistName.txt"
  songGenres="$songGenres$allGenres | "
done

#Add the tracks popularity into the Song file.
echo 'Track popularity: '$(jq .popularity API_Datas/SongTempDatas/Song$typeOfAnalysis.JSON) >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"

#Add the tracks genres into the Song file.
songGenres="${songGenres:1:-2}"
echo 'Track Possible Genres: '$songGenres >>"Condensed_Datas/SongDatas/$nameOfTrack.txt"

#Need To Start working from this part.
#typeOfAnalysis='albums'
#URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
#curl --request GET \
#  --url $URL \
#  --header 'Authorization: Bearer '$Token \
#  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#
#Need to obtain the bpm from audio_analysis curl.
COMMENT

exit 0
