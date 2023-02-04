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

  rm "API_Datas/PlaylistTempDatas/$2.JSON"
}

getCondensedDataFromPlayList() {
  #$1 = filePath.
  #$2 = playlist_id.
  playlist_name=$(trimQuotes "$(jq .name $1)")
  echo "$playlist_name" >"Condensed_Datas/PlaylistDatas/$2.txt"
  playlist_length=$(jq '.tracks.items | length' $1)
  for ((i = 0; i < $playlist_length; i++)); do
    song_id=$(trimQuotes "$(jq .tracks.items[$i].track.id $1)")
    song_name=$(trimQuotes "$(jq .tracks.items[$i].track.name $1)")
    getTrackJSON $Token $song_id
    echo "$song_name" >>"Condensed_Datas/PlaylistDatas/$2.txt"
  done
}

getTrackJSON() {
  #1 = token.
  #2 = track_id.
  URL='https://api.spotify.com/v1/tracks/'$2

  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$1 \
    --header 'Content-Type: application/json' >"API_Datas/SongTempDatas/$2.JSON"
  #
  getCondensedDataFromTrack "API_Datas/SongTempDatas/$2.JSON" $2

  rm "API_Datas/SongTempDatas/$2.JSON"
}

getCondensedDataFromTrack() {
  #$1 = filePath
  #$2 = track_id.

  #get track name.
  track_name=$(trimQuotes "$(jq .name $1)")
  #get track popularity.
  track_popularity=$(jq .popularity $1)

  #get track possible genres.
  artists_length=$(jq '.artists | length' $1)
  genres=""
  for ((j = 0; j < artists_length; j++)); do
    artist_id=$(trimQuotes "$(jq .artists[$j].id $1)")
    genres+=$(getApproximateGenres $Token $artist_id)
  done

  #get track MISC infos.
  track_misc_info="$(getTrackMISC $Token $2)"

  echo "$track_name" >"Condensed_Datas/SongDatas/$2.txt"
  echo "$track_popularity" >>"Condensed_Datas/SongDatas/$2.txt"
  echo "$track_misc_info" >>"Condensed_Datas/SongDatas/$2.txt"
  echo "$genres" >>"Condensed_Datas/SongDatas/$2.txt"

}

getApproximateGenres() {
  #$1 = token.
  #$2 = artist_id.
  URL='https://api.spotify.com/v1/artists/'$2
  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$1 \
    --header 'Content-Type: application/json' >"API_Datas/ArtistsTempDatas/$2.JSON"
  #

  #Create Song Genres var
  genresLength=$(jq '.genres | length' "API_Datas/ArtistsTempDatas/$2.JSON")
  for ((z = 0; z < genresLength; z++)); do
    addGenres=$(trimQuotes "$(jq .genres[$z] "API_Datas/ArtistsTempDatas/$2.JSON")")
    echo $addGenres
  done

  rm "API_Datas/ArtistsTempDatas/$2.JSON"
}

getTrackMISC() {
  #$1 = token.
  #$2 = track_id.
  URL='https://api.spotify.com/v1/audio-features/'$2
  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$1 \
    --header 'Content-Type: application/json' >"API_Datas/SongTempMISCDatas/$2.JSON"
  #
  track_tempo=$(jq .tempo "API_Datas/SongTempMISCDatas/$2.JSON")
  track_valence=$(jq .valence "API_Datas/SongTempMISCDatas/$2.JSON")
  track_energy=$(jq .energy "API_Datas/SongTempMISCDatas/$2.JSON")
  track_danceability=$(jq .danceability "API_Datas/SongTempMISCDatas/$2.JSON")
  track_acousticness=$(jq .acousticness "API_Datas/SongTempMISCDatas/$2.JSON")
  echo "$track_tempo"$'\n'"$track_valence"$'\n'"$track_energy"$'\n'"$track_danceability"$'\n'"$track_acousticness"

  rm "API_Datas/SongTempMISCDatas/$2.JSON"
}

main() {
  Token=$(returnToken)
  getPlaylistJSON $Token $playlist_id
}

#program begin here
playlist_id=$1
main

exit 0
