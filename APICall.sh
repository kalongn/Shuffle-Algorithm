#!/bin/bash

Token=$(node fetchToken.js)

typeOfAnalysis='tracks'
URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$1

curl --request GET \
  --url $URL \
  --header 'Authorization: Bearer '$Token \
  --header 'Content-Type: application/json' >SongTempDatas/Song$typeOfAnalysis.JSON
#
nameOfTrack=$(jq .name SongTempDatas/Song$typeOfAnalysis.JSON)

#Create the Song file
echo "" >SongDatas/"$nameOfTrack".txt

#Add the Track name into the Song file
echo 'Track Name: '$nameOfTrack >>"SongDatas/$nameOfTrack.txt"

#Add the Track ID into the Song file
echo 'Track ID: '$(jq .id "SongTempDatas/Song$typeOfAnalysis.JSON") >>"SongDatas/$nameOfTrack.txt"

#Add all the artists name, ID into this Song file.
artistsLength=$(jq '.artists | length' "SongTempDatas/Song$typeOfAnalysis.JSON")
for ((i = 1; i <= $artistsLength; i++)); do
  indexArr=$i-1
  #Add Artist ID / Name into Song File
  artistName=$(jq .artists[$indexArr].name "SongTempDatas/Song$typeOfAnalysis.JSON")
  artistID=$(jq .artists[$indexArr].id "SongTempDatas/Song$typeOfAnalysis.JSON")
  echo 'Artist '$i' Name: '$artistName >>"SongDatas/$nameOfTrack.txt"
  echo 'Artist '$i' Id: '$artistID >>"SongDatas/$nameOfTrack.txt"
  #Create Artist File respective to this song.
  echo '' >"ArtistsTempDatas/$artistName.JSON"
  artistID="${artistID:1:-1}"
  URL='https://api.spotify.com/v1/artists/'$artistID
  echo $URL
  curl --request GET \
    --url $URL \
    --header 'Authorization: Bearer '$Token \
    --header 'Content-Type: application/json' >"ArtistsTempDatas/$artistName.JSON"
  #

  #Create the actual data file via a txt file.
  echo '' >"ArtistsDatas/$artistName.txt"
  #Add artist name, genres and stats into a this new txt file.
  echo 'Artist Name: '$artistName >>"ArtistsDatas/$artistName.txt"
  echo 'Artist Popularity: '$(jq .popularity "ArtistsTempDatas/$artistName.JSON") >>"ArtistsDatas/$artistName.txt"
  echo 'Artist total followers:'$(jq .followers.total "ArtistsTempDatas/$artistName.JSON") >>"ArtistsDatas/$artistName.txt"
  genresLength=$(jq '.genres | length' "ArtistsTempDatas/$artistName.JSON")
  allGenres=''
  for ((z = 0; z < $genresLength; z++)); do 
    if [ $z == $((genresLength-1)) ]; then
      allGenres="$allGenres"$(jq .genres[$z] "ArtistsTempDatas/$artistName.JSON")
    else
      allGenres="$allGenres"$(jq .genres[$z] "ArtistsTempDatas/$artistName.JSON")" | "
    fi
  done
  echo 'Artist genres: '$allGenres >>"ArtistsDatas/$artistName.txt"
done

#Add the tracks popularity into the Song file.
echo 'Track popularity: '$(jq .popularity SongTempDatas/Song$typeOfAnalysis.JSON) >>"SongDatas/$nameOfTrack.txt"

#Need To Start working from this part.
#typeOfAnalysis='albums'
#URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
#curl --request GET \
#  --url $URL \
#  --header 'Authorization: Bearer '$Token \
#  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#

#typeOfAnalysis='artists'
#URL='https://api.spotify.com/v1/'$typeOfAnalysis'/'$2
#curl --request GET \
#  --url $URL \
#  --header 'Authorization: Bearer '$Token \
#  --header 'Content-Type: application/json' > SongTempDatas/Song$typeOfAnalysis.JSON
#

exit 0
